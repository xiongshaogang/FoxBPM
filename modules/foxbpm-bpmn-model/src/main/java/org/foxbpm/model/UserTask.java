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
 * @author ych
 */
package org.foxbpm.model;

import java.util.List;

/**
 * 人工任务
 * @author ych
 *
 */
public class UserTask extends Task {

	
	private static final long serialVersionUID = 1L;

	/**
	 * 操作表单
	 */
	protected String formUri;

	/**
	 * 浏览表单
	 */
	protected String formUriView;

	/**
	 * 任务类型
	 */
	protected String taskType;

	/**
	 * 任务主题
	 */
	protected String subject;

	/**
	 * 领取方式
	 */
	protected String claimType;

	/**
	 * 任务命令集合
	 */
	protected List<TaskCommand> taskCommands;

	/**
	 * 处理者集合
	 */
	protected List<Connector> actorConnectors;

	/**
	 * 任务描述
	 */
	protected String taskDescription;

	/**
	 * 完成后的任务描述
	 */
	protected String completeDescription;

	/**
	 * 期望执行时间
	 */
	protected double expectedTime;

	/**
	 * 表单参数
	 */
	protected List<FormParam> formParams;

	public String getFormUri() {
		return formUri;
	}

	public void setFormUri(String formUri) {
		this.formUri = formUri;
	}

	public String getFormUriView() {
		return formUriView;
	}

	public void setFormUriView(String formUriView) {
		this.formUriView = formUriView;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public List<TaskCommand> getTaskCommands() {
		return taskCommands;
	}

	public void setTaskCommands(List<TaskCommand> taskCommands) {
		this.taskCommands = taskCommands;
	}

	public List<Connector> getActorConnectors() {
		return actorConnectors;
	}

	public void setActorConnectors(List<Connector> actorConnectors) {
		this.actorConnectors = actorConnectors;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getCompleteDescription() {
		return completeDescription;
	}

	public void setCompleteDescription(String completeDescription) {
		this.completeDescription = completeDescription;
	}

	public double getExpectedTime() {
		return expectedTime;
	}

	public void setExpectedTime(double expectedTime) {
		this.expectedTime = expectedTime;
	}

	public List<FormParam> getFormParams() {
		return formParams;
	}

	public void setFormParams(List<FormParam> formParams) {
		this.formParams = formParams;
	}

}
