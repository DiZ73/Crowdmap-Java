/*******************************************************************************
 * Copyright (c) 2010 - 2013 Ushahidi Inc.
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
package com.crowdmap.java.sdk.service;

import com.crowdmap.java.sdk.json.Session;
import com.crowdmap.java.sdk.model.form.LoginForm;

import static com.crowdmap.java.sdk.net.CrowdmapHttpClient.METHOD_POST;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.LIMIT;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.OFFSET;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.SEGMENT_LOGIN;
import static com.crowdmap.java.sdk.net.ICrowdmapConstants.SEGMENT_SESSION;

/**
 * Login service
 */
public class SessionService extends CrowdmapService {

    public SessionService() {
    }

    /**
     * Login a user. POST /session/login
     *
     * @param form The login form.
     * @return {@link com.crowdmap.java.sdk.json.Session}
     */
    public Session login(LoginForm form) {

        // Build the URL for the login endpoint
        StringBuilder url = new StringBuilder(SEGMENT_SESSION);
        url.append(SEGMENT_LOGIN);

        setApiKey(METHOD_POST, url.toString());
        // Send a post request to login
        return fromString(client.multipartPost(url.toString(), form.getParameters()),
                Session.class);
    }

    public SessionService limit(int limit) {
        if (limit > 0) {
            getHttpClient().setRequestParameters(LIMIT, String.valueOf(limit));
        }
        return this;
    }

    public SessionService offset(int offset) {
        if (getHttpClient().getRequestParameters().containsKey(LIMIT)) {
            throw new IllegalArgumentException("Requires that a limit be set.");
        }

        getHttpClient().setRequestParameters(OFFSET, String.valueOf(offset));
        return this;
    }

    @Override
    public SessionService setSessionToken(String sessionToken) {
        return null;
    }
}
