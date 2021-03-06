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
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.diagramview.svg;

/**
 * 模版名称的定义类，和BPMN2.0下的SVG文件名进行映射
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public final class SVGTemplateNameConstant {
	/**
	 * SVG 模板容器
	 */
	public static final String SVG_TEMPLATE = "svg_template.svg";
	/**
	 * start_event
	 */
	public static final String TEMPLATE_STARTEVENT_NONE = "startevent/none.svg";
	public static final String TEMPLATE_STARTEVENT_TIMER = "startevent/timer.svg";
	public static final String TEMPLATE_STARTEVENT_SIGNAL = "startevent/signal.svg";
	public static final String TEMPLATE_STARTEVENT_MULTIPLE = "startevent/multiple.svg";
	public static final String TEMPLATE_STARTEVENT_MULTIPLE_PARALLEL = "startevent/multiple.parallel.svg";
	public static final String TEMPLATE_STARTEVENT_MESSAGE = "startevent/message.svg";
	public static final String TEMPLATE_STARTEVENT_ESCALATION = "startevent/escalation.svg";
	public static final String TEMPLATE_STARTEVENT_ERROR = "startevent/error.svg";
	public static final String TEMPLATE_STARTEVENT_CONDITIONAL = "startevent/conditional.svg";
	public static final String TEMPLATE_STARTEVENT_COMPENSATION = "startevent/compensation.svg";

	/**
	 * end_event
	 */
	public static final String TEMPLATE_ENDEVENT_NONE = "endevent/none.svg";
	public static final String TEMPLATE_ENDEVENT_CANCEL = "endevent/cancel.svg";
	public static final String TEMPLATE_ENDEVENT_SIGNAL = "endevent/signal.svg";
	public static final String TEMPLATE_ENDEVENT_MULTIPLE = "endevent/multiple.svg";
	public static final String TEMPLATE_ENDEVENT_MESSAGE = "endevent/message.svg";
	public static final String TEMPLATE_ENDEVENT_ESCALATION = "endevent/escalation.svg";
	public static final String TEMPLATE_ENDEVENT_ERROR = "endevent/error.svg";
	public static final String TEMPLATE_ENDEVENT_TERMINATE = "endevent/terminate.svg";
	public static final String TEMPLATE_ENDEVENT_COMPENSATION = "endevent/compensation.svg";

	public static final String TEMPLATE_CATCHEVENT_TIMER = "intermediateevent/timer.svg";

	/**
	 * activity
	 */
	public static final String TEMPLATE_ACTIVITY_TASK = "activity/task.svg";

	/**
	 * call activity
	 */
	public static final String TEMPLATE_ACTIVITY_CALLACTIVITY = "activity/callactivity.svg";
	/**
	 * sub process
	 */
	public static final String TEMPLATE_ACTIVITY_SUBPROCESS = "activity/subprocess.expanded.svg";
	/**
	 * sub process collapsed
	 */
	public static final String TEMPLATE_ACTIVITY_SUBPROCESS_COLLAPSED = "activity/subprocess.collapsed.svg";

	/**
	 * connector
	 */
	public static final String TEMPLATE_CONNECTOR_SEQUENCEFLOW = "connector/sequenceflow.svg";
	/**
	 * connector
	 */
	public static final String TEMPLATE_CONNECTOR_ASSOCIATION = "connector/association.undirected.svg";

	/**
	 * gateway
	 */
	public static final String TEMPLATE_GATEWAY_EXCLUSIVE = "gateway/exclusive.databased.svg";
	public static final String TEMPLATE_GATEWAY_INCLUSIVE = "gateway/inclusive.svg";
	public static final String TEMPLATE_GATEWAY_PARALLEL = "gateway/parallel.svg";

	/**
	 * lane
	 */
	public static final String TEMPLATE_LANESET = "swimlane/lane.svg";

	/**
	 * group
	 */
	public static final String TEMPLATE_GROUP = "artifact/group.svg";

	/**
	 * text annotation
	 */
	public static final String TEMPLATE_TEXTANNOTATION = "artifact/text.annotation.svg";

}
