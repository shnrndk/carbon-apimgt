/*
 * Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.gateway.handlers;

import org.apache.http.HttpHeaders;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.apimgt.gateway.APILoggerManager;
import org.wso2.carbon.apimgt.gateway.APIMgtGatewayConstants;
import org.wso2.carbon.apimgt.impl.APIConstants;

import java.util.Map;

/**
 * Provides util methods for the LogsHandler
 */
class LogUtils {

    protected static String getAuthorizationHeader(Map headers) {
        return (String) headers.get(HttpHeaders.AUTHORIZATION);
    }

    protected static String getCorrelationHeader(Map headers) {
        return (String) headers.get(APIConstants.AM_ACTIVITY_ID);
    }

    protected static String getOrganizationIdHeader(Map headers) {
        return (String) headers.get("organization-id");
    }

    protected static String getSourceIdHeader(Map headers) {
        return (String) headers.get("source-id");
    }

    protected static String getApplicationIdHeader(Map headers) {
        return (String) headers.get("application-id");
    }

    protected static String getUuidHeader(Map headers) {
        return (String) headers.get("uuid");
    }

    protected static String getAPIName(org.apache.synapse.MessageContext messageContext) {
        return (String) messageContext.getProperty("SYNAPSE_REST_API");
    }

    protected static String getAPICtx(org.apache.synapse.MessageContext messageContext) {
        return (String) messageContext.getProperty("REST_API_CONTEXT");
    }

    protected static String getRestMethod(org.apache.synapse.MessageContext messageContext) {
        org.apache.axis2.context.MessageContext axis2MsgContext = ((Axis2MessageContext) messageContext)
                .getAxis2MessageContext();
        return (String) axis2MsgContext.getProperty("HTTP_METHOD");
    }

    protected static String getRestHttpResponseStatusCode(org.apache.synapse.MessageContext messageContext) {
        org.apache.axis2.context.MessageContext axis2MsgContext = ((Axis2MessageContext) messageContext)
                .getAxis2MessageContext();
        return String.valueOf(axis2MsgContext.getProperty("HTTP_SC"));
    }

    protected static String getURLPrx(org.apache.synapse.MessageContext messageContext) {
        org.apache.axis2.context.MessageContext axis2MsgContext = ((Axis2MessageContext) messageContext)
                .getAxis2MessageContext();
        return (String) axis2MsgContext.getProperty("REST_URL_PREFIX");
    }

    protected static String getTo(org.apache.synapse.MessageContext messageContext) {
        org.apache.axis2.context.MessageContext axis2MsgContext = ((Axis2MessageContext) messageContext)
                .getAxis2MessageContext();
        return (String) axis2MsgContext.getProperty("REST_URL_POSTFIX");
    }

    protected static String getElectedResource(org.apache.synapse.MessageContext messageContext) {
        return (String) messageContext.getProperty("API_ELECTED_RESOURCE");
    }

    protected static String getResourceCacheKey(org.apache.synapse.MessageContext messageContext){
        return (String) messageContext.getProperty("API_RESOURCE_CACHE_KEY");
    }

    protected static String getRestReqFullPath(org.apache.synapse.MessageContext messageContext) {
        return (String) messageContext.getProperty("REST_FULL_REQUEST_PATH");
    }

    protected static Map getTransportHeaders(org.apache.synapse.MessageContext messageContext) {
        return (Map) ((Axis2MessageContext) messageContext).getAxis2MessageContext()
                .getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
    }

    protected static String getApplicationName(org.apache.synapse.MessageContext messageContext){
        return (String) messageContext.getProperty(APIMgtGatewayConstants.APPLICATION_NAME);
    }

    protected static String getConsumerKey(org.apache.synapse.MessageContext messageContext){
        return (String) messageContext.getProperty(APIMgtGatewayConstants.CONSUMER_KEY);
    }

    protected static String getTransportInURL(org.apache.synapse.MessageContext messageContext) {
        org.apache.axis2.context.MessageContext axis2MsgContext = ((Axis2MessageContext) messageContext)
                .getAxis2MessageContext();
        String transportInURL = (String) axis2MsgContext.getProperty("TransportInURL");
        return transportInURL.substring(1);
    }

    protected static String getHTTPMethod(org.apache.synapse.MessageContext messageContext) {
        org.apache.axis2.context.MessageContext axis2MsgContext = ((Axis2MessageContext) messageContext)
                .getAxis2MessageContext();
        String httpMethod = (String) axis2MsgContext.getProperty("HTTP_METHOD");
        return httpMethod;
    }

    protected static String getMatchingLogLevel(MessageContext ctx,
                                                Map<APILoggerManager.logResourceProperties, String> logProperties) {

        String apiCtx = LogUtils.getTransportInURL(ctx);
        String apiHttpMethod = LogUtils.getHTTPMethod(ctx);
        String apiLogLevel = null;
        String resourceLogLevel = null;
        String resourcePath = null;
        String resourceMethod = null;
        for (Map.Entry<APILoggerManager.logResourceProperties, String> entry : logProperties.entrySet()) {
            APILoggerManager.logResourceProperties key = entry.getKey();
            key.getResourceMethod();
            String apiResourcePath = apiCtx.split("/", 3)[2];
            if (("/" + apiResourcePath).equals(key.getResourcePath()) && apiHttpMethod.equals(key.getResourceMethod())) {
                if (key.getContext().startsWith(key.getContext() + "/") || key.getContext().equals(key.getContext())) {
                    resourceLogLevel = entry.getValue();
                    resourcePath = key.getResourcePath();
                    resourceMethod = key.getResourceMethod();
                }
            } else if (key.getResourcePath() == null && key.getResourceMethod() == null) {
                if (key.getContext().startsWith(key.getContext() + "/") || key.getContext().equals(key.getContext())) {
                    apiLogLevel = entry.getValue();
                }
            }
        }
        boolean isResourceLevelHasHighPriority = false;
        if (apiLogLevel != null || resourceLogLevel != null) {
            switch (resourceLogLevel) {
                case APIConstants.LOG_LEVEL_FULL:
                    isResourceLevelHasHighPriority = true;
                    break;
                case APIConstants.LOG_LEVEL_STANDARD:
                    if (apiLogLevel.equals(APIConstants.LOG_LEVEL_BASIC)
                            || apiLogLevel.equals(APIConstants.LOG_LEVEL_OFF)) {
                        isResourceLevelHasHighPriority = true;
                        break;
                    } else {
                        break;
                    }
                case APIConstants.LOG_LEVEL_BASIC:
                    if (apiLogLevel.equals(APIConstants.LOG_LEVEL_OFF)) {
                        isResourceLevelHasHighPriority = true;
                    } else {
                        break;
                    }
                case APIConstants.LOG_LEVEL_OFF:
                    break;
            }
            if (isResourceLevelHasHighPriority) {
                ctx.setProperty(LogsHandler.LOG_LEVEL, resourceLogLevel);
                ctx.setProperty(LogsHandler.RESOURCE_PATH, resourcePath);
                ctx.setProperty(LogsHandler.RESOURCE_METHOD, resourceMethod);
                ctx.setProperty("API_TO", apiCtx);
                return resourceLogLevel;
            } else {
                ctx.setProperty(LogsHandler.LOG_LEVEL, resourceLogLevel);
                ctx.setProperty("API_TO", apiCtx);
                return apiLogLevel;
            }
        }
        return null;
    }
}