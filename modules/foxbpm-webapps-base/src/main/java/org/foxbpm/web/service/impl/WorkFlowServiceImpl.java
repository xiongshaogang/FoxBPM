/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author yangguangftlp
 */
package org.foxbpm.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.web.common.constant.FoxbpmWebContextAttributeNameDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.DateUtil;
import org.foxbpm.web.common.util.JSONUtil;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.service.interfaces.IWorkFlowService;
import org.springframework.stereotype.Service;

/**
 * 工作流服务实现类
 * 
 * @author yangguangftlp
 * @date 2014年6月11日
 */
@Service("workFlowServiceImpl")
public class WorkFlowServiceImpl extends AbstWorkFlowService implements IWorkFlowService {

	@Override
	public List<Map<String, Object>> queryProcessDef(Pagination<String> pageInfor, Map<String, Object> params) throws FoxbpmWebException {
		// 返回结果
		List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
		try {
			// 创建流程定义查询
			ProcessDefinitionQuery pdq = modelService.createProcessDefinitionQuery();
			String processName = StringUtil.getString(params.get("queryProcessName"));
			if (StringUtil.isNotEmpty(processName)) {
				pdq.processDefinitionNameLike(processName);
			}
			String processId = StringUtil.getString(params.get("queryProcessId"));
			if (StringUtil.isNotEmpty(processId)) {
				pdq.processDefinitionKeyLike(processId);
			}
			String processCategory = StringUtil.getString(params.get("queryType"));
			if (StringUtil.isNotEmpty(processCategory)) {
				pdq.processDefinitionCategoryLike(processCategory);
			}
			pdq.orderByDeploymentId().desc();
			List<ProcessDefinition> pdList = null;
			if (null == pageInfor) {
				pdList = pdq.list();
			} else {
				pdList = pdq.listPagination(pageInfor.getPageIndex(), pageInfor.getPageSize());
				pageInfor.setTotal(StringUtil.getInt(pdq.count()));
			}
			Map<String, Object> attrMap = null;
			for (int i = 0, size = (null == pdList) ? 0 : pdList.size(); i < size; i++) {
				attrMap = pdList.get(i).getPersistentState();
				attrMap.put("formUrl", "startTask.action");
				resultData.add(attrMap);
			}
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultData;
	}

	@Override
	public List<Map<String, Object>> queryProcessInst(Pagination<String> pageInfor, Map<String, Object> params) throws FoxbpmWebException {
		// 返回结果
		List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
		try {
			ProcessInstanceQuery piq = runtimeService.createProcessInstanceQuery();
			// 获取查询条件参数
			String userId = StringUtil.getString(params.get("userId"));
			String processDefinitionKey = StringUtil.getString(params.get("processDefinitionKey"));
			String processInstanceId = StringUtil.getString(params.get("processInstanceId"));
			String processDefinitionName = StringUtil.getString(params.get("processDefinitionName"));
			String title = StringUtil.getString(params.get("title"));
			String bizKey = StringUtil.getString(params.get("bizKey"));
			String initor = StringUtil.getString(params.get("initor"));
			String status = StringUtil.getString(params.get("status"));
			String processType = StringUtil.getString(params.get("processType"));

			String dss = StringUtil.getString(params.get("startTimeS"));
			String dse = StringUtil.getString(params.get("startTimeE"));
			if (StringUtil.isNotEmpty(processDefinitionKey)) {
				piq.processDefinitionKey(processDefinitionKey);
			}
			if (StringUtil.isNotEmpty(processInstanceId)) {
				piq.processInstanceId(processInstanceId);
			}
			if (StringUtil.isNotEmpty(title)) {
				piq.subjectLike(title);
			}
			if (StringUtil.isNotEmpty(bizKey)) {
				piq.processInstanceBusinessKeyLike(bizKey);
			}
			if (StringUtil.isNotEmpty(status)) {
				piq.processInstanceStatus(status);
			}

			if (StringUtil.isNotEmpty(initor)) {
				piq.initiator(initor);
			}

			if (StringUtil.isNotEmpty(processType)) {
				if (processType.equals("initor")) {
					piq.initiator(userId);
				} else {
					piq.taskParticipants(userId);
				}

			}
			if (StringUtil.isNotEmpty(processDefinitionName)) {
				piq.processDefinitionNameLike(processDefinitionName);
			}
			Date dates = null;
			Date datee = null;

			if (StringUtil.isNotEmpty(dss)) {
				dates = DateUtil.stringToDate(dss, "yyyy-MM-dd");
			}
			if (StringUtil.isNotEmpty(dse)) {
				String endTime = "235959999";
				dse += endTime;
				datee = DateUtil.stringToDate(dse, "yyyy-MM-ddHHmmssSSS");
			}
			if (null != dates) {
				piq.startTimeBefore(dates);
			}
			if (null != datee) {
				piq.startTimeAfter(datee);
			}

			List<ProcessInstance> piList = null;
			piq.orderByUpdateTime().desc();
			if (null == pageInfor) {
				piList = piq.list();
			} else {
				// 执行分页查询
				piList = piq.listPagination(pageInfor.getPageIndex(), pageInfor.getPageSize());
				// 设置分页信息
				pageInfor.setTotal(StringUtil.getInt(piq.count()));
			}
			// 流程实例属性集
			Map<String, Object> attrMap = null;
			ProcessInstance pi = null;
			for (int i = 0, size = (null == piList) ? 0 : piList.size(); i < size; i++) {
				pi = piList.get(i);
				attrMap = new HashMap<String, Object>();
				attrMap.putAll(pi.getPersistentState());
				resultData.add(attrMap);
				attrMap.put("processDefinitionName", modelService.getProcessDefinition(pi.getProcessDefinitionId()).getName());
			}
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultData;
	}

	@Override
	public Map<String, Object> queryTaskDetailInfor(Map<String, Object> params) {
		// 返回结果
		Map<String, Object> resultData = new HashMap<String, Object>();
		try {
			String processInstanceId = StringUtil.getString(params.get("processInstanceId"));
			ProcessInstanceQuery piq = runtimeService.createProcessInstanceQuery();
			if (StringUtil.isNotEmpty(processInstanceId)) {
				ProcessInstance processInstance = piq.processInstanceId(processInstanceId).list().get(0);
				String processName = modelService.getProcessDefinition(processInstance.getProcessDefinitionId()).getName();
				TaskQuery tq = taskService.createTaskQuery();
				tq.processInstanceId(processInstanceId);
				tq.taskIsEnd().orderByEndTime().asc();
				List<Task> instances = tq.list();
				List<Map<String, Object>> instanceMaps = new ArrayList<Map<String, Object>>();
				for (Task tmp : instances) {
					Map<String, Object> instanceMap = tmp.getPersistentState();
					instanceMaps.add(instanceMap);
				}
				tq.taskNotEnd().orderByTaskCreateTime().asc();
				List<Task> instancesNotEnd = tq.list();
				List<Map<String, Object>> notEndInstanceMaps = new ArrayList<Map<String, Object>>();
				for (Task tmp : instancesNotEnd) {
					Map<String, Object> instanceMap = tmp.getPersistentState();
					notEndInstanceMaps.add(instanceMap);
				}
				Map<String, Map<String, Object>> postionMap = modelService.GetFlowGraphicsElementPosition(processInstance.getProcessDefinitionId());
				resultData.put("notEnddataList", notEndInstanceMaps);
				resultData.put("dataList", instanceMaps);
				resultData.put("positionInfo", JSONUtil.parseObject2JSON(postionMap));
				resultData.put("taskEndedJson", JSONUtil.parseObject2JSON(instanceMaps));
				resultData.put("taskNotEndJson", JSONUtil.parseObject2JSON(instancesNotEnd));
				resultData.put("processName", processName);
			}
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultData;
	}

	@Override
	public List<Map<String, Object>> queryToDoTask(Pagination<String> pageInfor, Map<String, Object> params) {

		// 返回结果
		List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
		try {
			TaskQuery taskQuery = taskService.createTaskQuery();
			// 获取基本查询条件参数
			String userId = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_USERID);
			String initiator = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_INITIATOR);
			String processDefinitionName = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PROCESSDEFINITIONNAME);
			String businessKey = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_BUSINESSKEY);
			String title = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_TITLE);
			String dss = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_ARRIVALTIMES);
			String dse = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_ARRIVALTIMEE);
			userId = "admin";
			// 处理查询参数
			if (StringUtil.isNotEmpty(userId)) {
				taskQuery.taskAssignee(userId);
				taskQuery.taskCandidateUser(userId);
			}
			if (StringUtil.isNotEmpty(initiator)) {
				taskQuery.initiator(initiator);
			}
			if (StringUtil.isNotEmpty(processDefinitionName)) {
				taskQuery.processDefinitionNameLike(processDefinitionName);
			}
			if (StringUtil.isNotEmpty(businessKey)) {
				taskQuery.businessKeyLike(businessKey);
			}
			if (StringUtil.isNotEmpty(title)) {
				taskQuery.taskDescriptionLike(title);
			}
			// 时间处理
			Date dates = null;
			Date datee = null;

			if (StringUtil.isNotEmpty(dss)) {
				dates = DateUtil.stringToDate(dss, "yyyy-MM-dd");
			}
			if (StringUtil.isNotEmpty(dse)) {
				String endTime = "235959999";
				dse += endTime;
				datee = DateUtil.stringToDate(dse, "yyyy-MM-ddHHmmssSSS");
			}
			if (dates != null) {
				taskQuery.taskCreatedAfter(datee);
			}
			if (datee != null) {
				taskQuery.taskCreatedBefore(dates);
			}
			taskQuery.orderByTaskCreateTime().desc();
			taskQuery.taskNotEnd();
			// 查询代办任务
			List<Task> taskList = null;
			if (null == pageInfor) {
				taskList = taskQuery.list();
			} else {
				taskList = taskQuery.listPagination(pageInfor.getPageIndex(), pageInfor.getPageSize());
				pageInfor.setTotal(StringUtil.getInt(taskQuery.count()));
			}
			Map<String, Object> attrMap = null;
			for (int i = 0, size = (null == taskList) ? 0 : taskList.size(); i < size; i++) {
				attrMap = taskList.get(i).getPersistentState();
				attrMap.put("formUri", "startTask.action");
				resultData.add(attrMap);
			}
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultData;
	}

	@Override
	public Map<String, Object> startTask(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> tmpres = new ArrayList<Map<String, Object>>();
		try {
			String taskId = StringUtil.getString(params.get("taskId"));
			String processDefinitionKey = StringUtil.getString(params.get("processDefinitionKey"));
			List<TaskCommand> list = null;
			if (StringUtil.isNotEmpty(taskId)) {
				list = taskService.getTaskCommandByTaskId(taskId);
			} else {
				list = taskService.getSubTaskCommandByKey(processDefinitionKey);
			}
			for (TaskCommand tmp : list) {
				tmpres.add(tmp.getPersistentState());
			}
			resultMap.put("commandList", tmpres);
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultMap;
	}

	@Override
	public ProcessInstance completeTask(Map<String, Object> params) {
		Authentication.setAuthenticatedUserId("admin");
		ProcessInstance processInstance = null;
		String taskId = StringUtil.getString(params.get("taskId"));
		String commandType = StringUtil.getString(params.get("commandType"));
		String commandId = StringUtil.getString(params.get("commandId"));
		String processDefinitionKey = StringUtil.getString(params.get("processDefinitionKey"));
		String businessKey = StringUtil.getString(params.get("businessKey"));
		String userId = StringUtil.getString(params.get("userId"));
		String taskComment = StringUtil.getString(params.get("_taskComment"));

		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		userId = "admin";
		// 命令类型，可以从流程引擎配置中查询 启动并提交为startandsubmit
		expandTaskCommand.setCommandType(commandType);
		// 设置提交人
		expandTaskCommand.setInitiator(userId);
		// 设置命令的id,需和节点上配置的按钮编号对应，会执行按钮中的脚本。
		expandTaskCommand.setTaskCommandId(commandId);
		expandTaskCommand.setTaskComment(taskComment);
		try {
			if (StringUtil.isNotEmpty(taskId)) {
				expandTaskCommand.setTaskId(taskId);
			} else {
				String processInstanceId = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey).getId();
				Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskNotEnd().singleResult();
				expandTaskCommand.setTaskId(task.getId());
			}
			processInstance = taskService.expandTaskComplete(expandTaskCommand, null);
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return processInstance;
	}

}