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
package com.crowdmap.java.sdk.resource;

import com.crowdmap.java.sdk.json.MediaJson;
import com.crowdmap.java.sdk.net.CrowdmapHttpClient;

import static com.crowdmap.java.sdk.net.ICrowdmapConstants.SEGMENT_MEDIA;

/**
 * Service for interacting with crowdmap's media API
 */
public class MediaResource extends CrowdmapResource {

    /**
     * Create the media service with a default crowdmap HTTP client
     */
    public MediaResource() {
        super();
    }

    /**
     * Create the media service with a configured crowdmap HTTP client
     */
    public MediaResource(CrowdmapHttpClient client) {
        super(client);
    }

    /**
     * Get media in crowdmap. GET /media
     */
    public MediaJson getMedia() {

        String response = client.get(SEGMENT_MEDIA);
        MediaJson mediaJson = fromString(response, MediaJson.class);
        return mediaJson;
    }

    /**
     * Get a specific media
     *
     * @param id The ID of the media
     * @return MediaJson Object
     */
    public MediaJson getMedia(String id) {
        StringBuilder url = new StringBuilder(SEGMENT_MEDIA);
        url.append("/");
        url.append(id);
        String response = client.get(url.toString());
        MediaJson mediaJson = fromString(response, MediaJson.class);
        return mediaJson;
    }
}