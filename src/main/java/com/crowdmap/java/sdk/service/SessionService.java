/*****************************************************************************
 ** Copyright (c) 2010 - 2012 Ushahidi Inc
 ** All rights reserved
 ** Contact: team@ushahidi.com
 ** Website: http://www.ushahidi.com
 **
 ** GNU Lesser General Public License Usage
 ** This file may be used under the terms of the GNU Lesser
 ** General Public License version 3 as published by the Free Software
 ** Foundation and appearing in the file LICENSE.LGPL included in the
 ** packaging of this file. Please review the following information to
 ** ensure the GNU Lesser General Public License version 3 requirements
 ** will be met: http://www.gnu.org/licenses/lgpl.html.
 **
 **
 ** If you have questions regarding the use of this file, please contact
 ** Ushahidi developers at team@ushahidi.com.
 **
 *****************************************************************************/
package com.crowdmap.java.sdk.service;

import com.crowdmap.java.sdk.json.Session;
import com.crowdmap.java.sdk.net.content.Body;

import static com.crowdmap.java.sdk.net.CrowdmapHttpClient.METHOD_POST;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.SEGMENT_LOGIN;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.SEGMENT_SESSION;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.SEGMENT_USERS;

/**
 * Login service
 */
public class SessionService extends CrowdmapService {

    public SessionService() {
    }

    /**
     * Login a user. POST /session/login
     *
     * @param username The user's username. The username field is typically the email address.
     * @param password The user's password
     * @return {@link com.crowdmap.java.sdk.json.Session}
     */
    public Session login(String username, String password) {

        // Build the URL for the login endpoint
        StringBuilder url = new StringBuilder(SEGMENT_SESSION);
        url.append(SEGMENT_LOGIN);

        // Pass the username and password to the login endpoint
        final Body body = new Body();
        body.addField("username", username);
        body.addField("password", password);

        setApiKey(METHOD_POST, SEGMENT_SESSION);
        // Send a post request to login
        return fromString(client.post(url.toString(), body),
                Session.class);
    }
}
