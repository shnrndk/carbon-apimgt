/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.apimgt.keymgt.token;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.wso2.carbon.apimgt.api.APIManagementException;

public class URLSafeJWTGeneratorTest {

    @Test
    public void testEncode() {
        URLSafeJWTGenerator urlSafeJWTGenerator = Mockito.mock(URLSafeJWTGenerator.class, Mockito.CALLS_REAL_METHODS);
        String stringToBeEncoded = "<<???>>";
        String expectedEncodedString = "PDw_Pz8-Pg";
        try {
            String actualEncodedString = urlSafeJWTGenerator.encode(stringToBeEncoded.getBytes());
            Assert.assertEquals(expectedEncodedString, actualEncodedString);
        } catch (APIManagementException e) {
            Assert.fail("APIManagementException thrown");
        }
    }
}
