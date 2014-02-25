/*******************************************************************************
 * Copyright (c) 2010 - 2014 Ushahidi Inc.
 * All rights reserved
 * Website: http://www.ushahidi.com
 *
 * GNU AFFERO GENERAL PUBLIC LICENSE Version 3 Usage
 * This file may be used under the terms of the GNU AFFERO GENERAL
 * PUBLIC LICENSE Version 3 as published by the Free Software
 * Foundation and appearing in the file LICENSE included in the
 * packaging of this file. Please review the following information to
 * ensure the GNU AFFERO GENERAL PUBLIC LICENSE Version 3 requirements
 * will be met: http://www.gnu.org/licenses/agpl.html.
 ******************************************************************************/

package com.crowdmap.java.sdk.mock;

import com.crowdmap.java.sdk.service.api.UtilityInterface;

import retrofit.MockRestAdapter;
import retrofit.RestAdapter;

/**
 * Created by eyedol on 2/22/14.
 */
public class MockCrowdmap {

    public static void main(String... args) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Endpoints.PRODUCTION.url)
                .build();

        MockRestAdapter mockRestAdapter = MockRestAdapter.from(restAdapter);

        MockUtilityService mMockUtilityService = new MockUtilityService();

        UtilityInterface utilityInterface = mockRestAdapter.create(UtilityInterface.class, mMockUtilityService);

        System.out.println("HeartBeat Endpoint: "+utilityInterface.heartbeat().toString() + "\n");
        System.out.println("About Endpoint: " + utilityInterface.about().toString() );
        new MockCrowdmap();
    }
}