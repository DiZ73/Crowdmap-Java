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

import com.crowdmap.java.sdk.json.About;
import com.crowdmap.java.sdk.json.Collaborators;
import com.crowdmap.java.sdk.json.Comments;
import com.crowdmap.java.sdk.json.Externals;
import com.crowdmap.java.sdk.json.Followers;
import com.crowdmap.java.sdk.json.Locations;
import com.crowdmap.java.sdk.json.MapSettings;
import com.crowdmap.java.sdk.json.MapTags;
import com.crowdmap.java.sdk.json.Maps;
import com.crowdmap.java.sdk.json.Media;
import com.crowdmap.java.sdk.json.Notifications;
import com.crowdmap.java.sdk.json.OEmbed;
import com.crowdmap.java.sdk.json.Owner;
import com.crowdmap.java.sdk.json.PostTags;
import com.crowdmap.java.sdk.json.Posts;
import com.crowdmap.java.sdk.json.Users;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the various
 */
public class CrowdmapServiceTest extends BaseServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAboutDeserialization() throws Exception {
        final String aboutString = "{\n"
                + "    \"version\": 1,\n"
                + "    \"limit_min\": 0,\n"
                + "    \"limit_max\": 1000,\n"
                + "    \"success\": true,\n"
                + "    \"status\": 200,\n"
                + "    \"timestamp\": 1375336265,\n"
                + "    \"qcount\": 2,\n"
                + "    \"elapsed\": \"0.0458s\"\n"
                + "}";

        About about = CrowdmapService.fromString(aboutString, About.class);
        assertNotNull(about);
        assertEquals(1, about.getVersion());
        assertEquals(true, about.isSuccess());
    }

    @Test
    public void testCollaboratorsDeserialization() throws Exception {
        final String json = "{\"maps_collaborators\":[{\"map_id\": 1910,\n"
                + "            \"users\": [\n"
                + "                {\n"
                + "                    \"user_id\": 3,\n"
                + "                    \"crowdmap_id\": \"eoAKIIKZLYW6RdvTK5IbJ9QCSuJKnNb8B5I75kBHUjn7tD13k7oSJ7ifWmrxGNFsnEQCvqB1OJqbj1ovDD8bKGMVWTn5ggQ7P16ty3R6KYaNpvXhF3gF4K0ZFnwsqTxM\",\n"
                + "                    \"crowdmap_id_h\": \"8be2ac7a1ce39e06e7043c02e23a1438\",\n"
                + "                    \"username\": \"brianherbert\",\n"
                + "                    \"name\": \"Brian Herbert\",\n"
                + "                    \"bio\": \"One of the men behind the curtain.\",\n"
                + "                    \"plus\": true,\n"
                + "                    \"baselayer\": \"crowdmap_cucumber\",\n"
                + "                    \"instagram_auto_post\": true,\n"
                + "                    \"twitter_auto_post\": false,\n"
                + "                    \"date_registered\": 1363603003,\n"
                + "                    \"banned\": \"0\",\n"
                + "                    \"avatar\": \"\\/\\/b25c7ada827abcbc0630-5454a9e6f7100566866dd221e5013c79.ssl.cf2.rackcdn.com\\/51b14da2c91ab2.71208892_c.png\"\n"
                + "                }\n"
                + "            ],\n"
                + "            \"user_id\": 3}]}";
        Collaborators collaborators = CrowdmapService.fromString(json, Collaborators.class);
        assertNotNull(collaborators);
        assertEquals(1910, collaborators.getCollaborators().get(0).getMapId());
        assertEquals(3, collaborators.getCollaborators().get(0).getUsers().get(0).getId());
        assertEquals("brianherbert",
                collaborators.getCollaborators().get(0).getUsers().get(0).getUsername());
    }

    @Test
    public void testCommentDeserialization() throws Exception {
        final String json
                = " {\"comments\":[{\"comment_id\":678,\"users\":[{\"user_id\":4,\"crowdmap_id\":\"K8HoFEFIN5JRp4a8K6XRxGjomLVH1snBBQXHw3hkW00v9xmQZmNNLi52P3Nq19daGpUcQ9O4JF0nZtnlGj3cZQ7L06E00LEiO31MnhCHNI82biiZ2eKyQkQ0NQAU6jT5\",\"crowdmap_id_h\":\"e4c4c42febb92a6264a4e9d80473884c\",\"username\":\"whiteafrican\",\"name\":\"Erik Hersman\",\"bio\":\"The @WhiteAfrican. Co-founder of Ushahidi and the iHub in Nairobi.\",\"plus\":true,\"baselayer\":\"crowdmap_cucumber\",\"instagram_auto_post\":true,\"twitter_auto_post\":false,\"twitter_auto_post_retweets\":false,\"date_registered\":1363603003,\"banned\":false,\"avatar\":\"\\/\\/www.gravatar.com\\/avatar\\/dd7a1c066790ac8dcab4ca43bb2ba9d9?r=PG&s=200&d=404\",\"badges\":[]}],\"post_id\":17097,\"map_id\":0,\"user_id\":4,\"comment\":\"<p>Ugh!<\\/p>\",\"date_posted\":1377838894,\"permissions\":{\"edit\":false,\"delete\":false},\"map\":null}],\"next\":false,\"curr\":\"https:\\/\\/api.crowdmap.com\\/v1\\/posts\\/17097\\/comments\\/?apikey=AmcoSiLOiRUXiiAPv4109d2a099ee87ba73f69b8d2e590d4f44c8df1a\",\"prev\":false,\"success\":true,\"status\":200,\"timestamp\":1378470104,\"qcount\":6,\"elapsed\":\"0.0506s\"} ";
        Comments comments = CrowdmapService.fromString(json, Comments.class);
        assertNotNull(comments);
        assertEquals(678, comments.getComments().get(0).getId());
        assertEquals(17097, comments.getComments().get(0).getPostId());
        assertEquals("<p>Ugh!</p>", comments.getComments().get(0).getComment());
    }

    @Test
    public void testPostsDeserialization() throws Exception {
        final String json
                = "{\"posts\":[{\"externals\":[],\"locations\":[{\"location_id\":21077,\"fsq_venue_id\":\"\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[-122.40641784668,37.785835266113]},\"name\":\"\",\"region\":\"\"}],\"maps\":[],\"media\":[],\"post_id\":800,\"users\":[{\"user_id\":2,\"crowdmap_id\":\"8bcd236563e61dfd3b85d16ddcb1bbb3cc8a3d176aafd397e80a7de8e89c563aafa91cfb272578be5625f5a96be0af5803765a90210ec7c61cd195f82d261532\",\"crowdmap_id_h\":\"b753d5eb1bf2237cefb5b5fe4b42bef4\",\"username\":\"evansims\",\"name\":\"Evan Sims\",\"bio\":\"Senior Developer at Ushahidi. \\u2014 A software craftsman from the mid-west. \\u2014 #Entrepreneur, #designer, #minimalist, #caffeine enthusiast. \\u2014 http:\\/\\/evansims.com\\/about\\/\",\"plus\":true,\"baselayer\":\"crowdmap_satellite\",\"instagram_auto_post\":false,\"twitter_auto_post\":true,\"twitter_auto_post_retweets\":true,\"date_registered\":1363602996,\"banned\":false,\"avatar\":\"\\/\\/b25c7ada827abcbc0630-5454a9e6f7100566866dd221e5013c79.ssl.cf2.rackcdn.com\\/5216d928b0ff90.48923912_c.gif\",\"badges\":[]}],\"owner_map_id\":0,\"media_id\":0,\"location_id\":21077,\"external_id\":0,\"externals_images_id\":0,\"message\":\"<p>Test.<\\/p>\",\"date_posted\":1377635313,\"public\":true,\"permissions\":{\"edit\":false,\"delete\":false},\"stub\":\"test\",\"tags\":[],\"likes\":[],\"comments\":[]}]}";
        Posts post = CrowdmapService.fromString(json, Posts.class);
        assertNotNull(post);
        assertEquals(800, post.getPosts().get(0).getPostId());
        assertEquals("<p>Test.</p>", post.getPosts().get(0).getMessage());
        assertEquals(21077, post.getPosts().get(0).getLocations().get(0).getLocationId());
        assertEquals("Point", post.getPosts().get(0).getLocations().get(0).getGeometry().getType());
        assertEquals(-122.40641784668,
                post.getPosts().get(0).getLocations().get(0).getGeometry().getCoordinates()[0]);
        assertEquals(37.785835266113,
                post.getPosts().get(0).getLocations().get(0).getGeometry().getCoordinates()[1]);
        assertEquals("evansims", post.getPosts().get(0).getUsers().get(0).getUsername());
    }

    @Test
    public void testExternalsDeserialization() throws Exception {
        final String json = "{\n"
                + "\"externals\": [\n"
                + "{\n"
                + "\"external_id\": 10664,\n"
                + "\"service_id\": 1,\n"
                + "\"provider\": \"Twitter\",\n"
                + "\"type\": \"html\",\n"
                + "\"id_on_service\": \"376883832215310336\",\n"
                + "\"content\": \"War on drugs? 'Them' against 'us' is out! http://t.co/yjJMDKDaN1 ▸ Top stories today via @HipHopHillbill @spacejam_mtgo @EBrownGomez\",\n"
                + "\"datetime\": 1378691082,\n"
                + "\"lat\": null,\n"
                + "\"lon\": null,\n"
                + "\"url\": \"https://twitter.com/harlechnnorfolk/statuses/376883832215310336\",\n"
                + "\"title\": null,\n"
                + "\"favicon_url\": \"https://twitter.com/favicons/favicon.ico\",\n"
                + "\"embed_html\": \"<blockquote class=\\\"twitter-tweet\\\"><p>War on drugs? &#39;Them&#39; against &#39;us&#39; is out! <a href=\\\"http://t.co/yjJMDKDaN1\\\">http://t.co/yjJMDKDaN1</a> ▸ Top stories today via <a href=\\\"https://twitter.com/HipHopHillbill\\\">@HipHopHillbill</a> <a href=\\\"https://twitter.com/spacejam_mtgo\\\">@spacejam_mtgo</a> <a href=\\\"https://twitter.com/EBrownGomez\\\">@EBrownGomez</a></p>&mdash; harlechnnorfolk (@harlechnnorfolk) <a href=\\\"https://twitter.com/harlechnnorfolk/statuses/376883832215310336\\\">September 9, 2013</a></blockquote>\\n\",\n"
                + "\"images\": []\n"
                + "}\n"
                + "],\n"
                + "\"next\": \"https://api.crowdmap.com/v1/externals/?apikey=AmcoSiLOiRUXiiAPv478e1113546b9c36d58547719c775a431ed5447e&limit=1&offset=1\",\n"
                + "\"curr\": \"https://api.crowdmap.com/v1/externals/?apikey=AmcoSiLOiRUXiiAPv478e1113546b9c36d58547719c775a431ed5447e&limit=1\",\n"
                + "\"prev\": false,\n"
                + "\"success\": true,\n"
                + "\"status\": 200,\n"
                + "\"timestamp\": 1378691222,\n"
                + "\"qcount\": 4,\n"
                + "\"elapsed\": \"0.1496s\"\n"
                + "}";
        Externals externals = CrowdmapService.fromString(json, Externals.class);
        assertNotNull(externals);
        assertNotNullOrEmpty("Externals is empty ", externals.getExternals());
        assertEquals(10664, externals.getExternals().get(0).getId());
        assertEquals(1, externals.getExternals().get(0).getServiceId());
        assertEquals("Twitter", externals.getExternals().get(0).getProvider());
        assertEquals("html", externals.getExternals().get(0).getType());
        assertEquals("376883832215310336", externals.getExternals().get(0).getIdOnService());
        assertEquals(
                "War on drugs? 'Them' against 'us' is out! http://t.co/yjJMDKDaN1 ▸ Top stories today via @HipHopHillbill @spacejam_mtgo @EBrownGomez",
                externals.getExternals().get(0).getContent());
        assertEquals("https://twitter.com/harlechnnorfolk/statuses/376883832215310336",
                externals.getExternals().get(0).getUrl());
        assertNull(externals.getExternals().get(0).getLat());
        assertNull(externals.getExternals().get(0).getLon());
        assertEquals("https://twitter.com/favicons/favicon.ico",
                externals.getExternals().get(0).getFaviconUrl());
        // Fails no matter what. commenting it out for now.
        assertEquals("<blockquote class=\"twitter-tweet\"><p>War on drugs? &#39;Them&#39; against &#39;us&#39; is out! <a href=\"http://t.co/yjJMDKDaN1\">http://t.co/yjJMDKDaN1</a> ▸ Top stories today via <a href=\"https://twitter.com/HipHopHillbill\">@HipHopHillbill</a> <a href=\"https://twitter.com/spacejam_mtgo\">@spacejam_mtgo</a> <a href=\"https://twitter.com/EBrownGomez\">@EBrownGomez</a></p>&mdash; harlechnnorfolk (@harlechnnorfolk) <a href=\"https://twitter.com/harlechnnorfolk/statuses/376883832215310336\">September 9, 2013</a></blockquote>\n", externals.getExternals().get(0).getEmbedHtml());
        assertEquals(
                "https://api.crowdmap.com/v1/externals/?apikey=AmcoSiLOiRUXiiAPv478e1113546b9c36d58547719c775a431ed5447e&limit=1&offset=1",
                externals.getNext());
        assertEquals(
                "https://api.crowdmap.com/v1/externals/?apikey=AmcoSiLOiRUXiiAPv478e1113546b9c36d58547719c775a431ed5447e&limit=1",
                externals.getCurr());
        assertEquals(true, externals.isSuccess());
        assertEquals(200, externals.getStatus());
        assertEquals(4, externals.getQcount());
    }

    @Test
    public void testMediaDeserialization() throws Exception {
        final String json
                = "{\"media\":[{\"media_id\":644,\"users\":[{\"user_id\":1,\"crowdmap_id\":\"\",\"crowdmap_id_h\":\"d41d8cd98f00b204e9800998ecf8427e\",\"username\":\"anonymous\",\"name\":\"Anonymous\",\"bio\":\"\",\"plus\":false,\"baselayer\":\"crowdmap_satellite\",\"instagram_auto_post\":false,\"twitter_auto_post\":false,\"twitter_auto_post_retweets\":false,\"date_registered\":1363602996,\"banned\":\"0\"}],\"user_id\":1,\"lat\":null,\"lon\":null,\"file_datetime\":null,\"upload_datetime\":1377027689,\"file_location\":\"https:\\/\\/b25c7ada827abcbc0630-5454a9e6f7100566866dd221e5013c79.ssl.cf2.rackcdn.com\\/\",\"filename\":\"5213c65f1682f7.87923465_o.jpg\",\"width\":\"779\",\"height\":\"580\",\"filename_l\":\"5213c65f1682f7.87923465_o.jpg\",\"l_width\":\"779\",\"l_height\":\"580\",\"filename_s\":\"5213c65f1682f7.87923465_o.jpg\",\"s_width\":\"779\",\"s_height\":\"580\",\"filename_t\":\"5213c668d19c9624944926.jpg\",\"t_width\":\"134\",\"t_height\":\"100\",\"mime\":\"image\\/png\",\"dominant_color\":\"203010\",\"webp\":false}]}";
        Media media = CrowdmapService.fromString(json, Media.class);
        assertNotNull(media);
        assertEquals(644, media.getMedia().get(0).getId());
        assertEquals(1, media.getMedia().get(0).getUsers().get(0).getId());
        assertEquals("d41d8cd98f00b204e9800998ecf8427e",
                media.getMedia().get(0).getUsers().get(0).getCrowdmapIdH());
        assertEquals("", media.getMedia().get(0).getUsers().get(0).getCrowdmapId());
        assertEquals("anonymous", media.getMedia().get(0).getUsers().get(0).getUsername());
        assertEquals("5213c65f1682f7.87923465_o.jpg", media.getMedia().get(0).getFilename());
        assertEquals("580", media.getMedia().get(0).getLargeHeight());
        assertEquals("779", media.getMedia().get(0).getLargeWidth());
        assertEquals("203010", media.getMedia().get(0).getDominantColor());
    }

    @Test
    public void testFollowersDeserialization() throws Exception {
        final String json = "{\n"
                + "\"following_maps\": [\n"
                + "{\n"
                + "\"users\": [\n"
                + "{\n"
                + "\"user_id\": 448,\n"
                + "\"crowdmap_id\": \"01e941cfe6bd0af9612514ab31d7ba3540d3100df20ac64db7dd7dd94063a87895fbaf05e0111e064d2d4a0fc4a4b2858aa0b792c5b06ad20289f582d7705f7e\",\n"
                + "\"crowdmap_id_h\": \"d49dfb4ccbc9fc9d5d34ff6d926bc590\",\n"
                + "\"username\": \"gmafort\",\n"
                + "\"name\": \"Gabriela Thomaz Mafort\",\n"
                + "\"bio\": \"\",\n"
                + "\"plus\": false,\n"
                + "\"baselayer\": \"crowdmap_satellite\",\n"
                + "\"instagram_auto_post\": false,\n"
                + "\"twitter_auto_post\": false,\n"
                + "\"twitter_auto_post_retweets\": false,\n"
                + "\"date_registered\": 1367883322,\n"
                + "\"banned\": \"0\",\n"
                + "\"avatar\": false\n"
                + "}\n"
                + "],\n"
                + "\"user_id\": 448,\n"
                + "\"map_id\": 536\n"
                + "}\n"
                + "],\n"
                + "\"next\": \"https://api.crowdmap.com/v1/maps/riodosjogos/followers/?apikey=godmode&limit=1&offset=1\",\n"
                + "\"curr\": \"https://api.crowdmap.com/v1/maps/riodosjogos/followers/?apikey=godmode&limit=1\",\n"
                + "\"prev\": false,\n"
                + "\"success\": true,\n"
                + "\"status\": 200,\n"
                + "\"timestamp\": 1378695432,\n"
                + "\"qcount\": 5,\n"
                + "\"elapsed\": \"0.1057s\"\n"
                + "}";
        Followers followers = CrowdmapService.fromString(json, Followers.class);
        assertNotNull(followers);
        assertNotNullOrEmpty("Followers is empty", followers.getFollowers());
        assertNotNullOrEmpty("Users is empty", followers.getFollowers().get(0).getUsers());
        assertEquals(448, followers.getFollowers().get(0).getUsers().get(0).getId());
        assertEquals(
                "01e941cfe6bd0af9612514ab31d7ba3540d3100df20ac64db7dd7dd94063a87895fbaf05e0111e064d2d4a0fc4a4b2858aa0b792c5b06ad20289f582d7705f7e",
                followers.getFollowers().get(0).getUsers().get(0).getCrowdmapId());
        assertEquals("gmafort", followers.getFollowers().get(0).getUsers().get(0).getUsername());
        assertEquals("Gabriela Thomaz Mafort",
                followers.getFollowers().get(0).getUsers().get(0).getName());
        assertEquals(536, followers.getFollowers().get(0).getMapId());
    }

    @Test
    public void testLocationDeserialization() throws Exception {
        final String json = "{\n"
                + "\"locations\": [\n"
                + "{\n"
                + "\"location_id\": 6393,\n"
                + "\"fsq_venue_id\": \"4e4d41bd18388d498172dcf0\",\n"
                + "\"geometry\": {\n"
                + "\"type\": \"Point\",\n"
                + "\"coordinates\": [\n"
                + "-73.67847,\n"
                + "42.72690005\n"
                + "]\n"
                + "},\n"
                + "\"name\": \"Moe's Southwest Grill\",\n"
                + "\"region\": \"\"\n"
                + "}\n"
                + "],\n"
                + "\"next\": \"https://api.crowdmap.com/v1/locations/?apikey=godmode&limit=1&offset=1\",\n"
                + "\"curr\": \"https://api.crowdmap.com/v1/locations/?apikey=godmode&limit=1\",\n"
                + "\"prev\": false,\n"
                + "\"success\": true,\n"
                + "\"status\": 200,\n"
                + "\"timestamp\": 1378700778,\n"
                + "\"qcount\": 3,\n"
                + "\"elapsed\": \"0.0361s\"\n"
                + "}";

        Locations location = CrowdmapService.fromString(json, Locations.class);
        assertNotNull(location);
        assertNotNullOrEmpty("Location is empty", location.getLocations());
        assertEquals(6393, location.getLocations().get(0).getLocationId());
        assertEquals("4e4d41bd18388d498172dcf0", location.getLocations().get(0).getFsqVenueId());
        assertNotNull(location.getLocations().get(0).getGeometry());
        assertEquals("Point", location.getLocations().get(0).getGeometry().getType());
        assertNotNull(location.getLocations().get(0).getGeometry().getCoordinates());
        assertEquals(-73.67847, location.getLocations().get(0).getGeometry().getCoordinates()[0]);
        assertEquals(42.72690005, location.getLocations().get(0).getGeometry().getCoordinates()[1]);
        assertEquals("Moe's Southwest Grill", location.getLocations().get(0).getName());
        assertEquals("", location.getLocations().get(0).getRegion());
        assertEquals(200, location.getStatus());
    }

    @Test
    public void testMapsDeserialization() throws Exception {
        final String json = "{\n"
                + "\"maps\": [\n"
                + "{\n"
                + "\"map_id\": 2464,\n"
                + "\"users\": [\n"
                + "{\n"
                + "\"user_id\": 3950,\n"
                + "\"crowdmap_id\": \"69b1d0b1cafca75cfb8ca8414a565be7a08495bbfe2257324109dbef7fe4f41b4adb9715c8ead6f797f16bffc5abbfc95aec4703e986097d98348bf974f92609\",\n"
                + "\"crowdmap_id_h\": \"de4d79b0b4cb30c0b791924a645e0b0c\",\n"
                + "\"username\": \"neo\",\n"
                + "\"name\": \"Debanshu Roy\",\n"
                + "\"bio\": \"\",\n"
                + "\"plus\": false,\n"
                + "\"baselayer\": \"crowdmap_satellite\",\n"
                + "\"instagram_auto_post\": false,\n"
                + "\"twitter_auto_post\": false,\n"
                + "\"twitter_auto_post_retweets\": false,\n"
                + "\"date_registered\": 1378658743,\n"
                + "\"banned\": \"0\",\n"
                + "\"avatar\": \"//www.gravatar.com/avatar/6d0723e8d8d1a72e9687057d0f3b23ba?r=PG&s=200&d=404\"\n"
                + "}\n"
                + "],\n"
                + "\"user_id\": 3950,\n"
                + "\"subdomain\": \"privateunaidedschoolssouthdelhi\",\n"
                + "\"name\": \"private unaided schoolssouthdelhi\",\n"
                + "\"description\": \"mapping private unaided schools in South Delhi for flagging the schools which are entitled to give 25% seats to children of the disadvantaged class according to the Right to Education.\",\n"
                + "\"banner\": null,\n"
                + "\"avatar\": \"//b25c7ada827abcbc0630-5454a9e6f7100566866dd221e5013c79.ssl.cf2.rackcdn.com/522d4db6a37797.24377648_c.jpg\",\n"
                + "\"public\": true,\n"
                + "\"moderation\": \"collaborator\",\n"
                + "\"date_created\": 1378700392,\n"
                + "\"followers\": 1,\n"
                + "\"posts\": 0\n"
                + "}\n"
                + "],\n"
                + "\"next\": \"https://api.crowdmap.com/v1/maps/?apikey=godmode&limit=1&offset=1\",\n"
                + "\"curr\": \"https://api.crowdmap.com/v1/maps/?apikey=godmode&limit=1\",\n"
                + "\"prev\": false,\n"
                + "\"success\": true,\n"
                + "\"status\": 200,\n"
                + "\"timestamp\": 1378702118,\n"
                + "\"qcount\": 10,\n"
                + "\"elapsed\": \"0.0993s\"\n"
                + "}";
        Maps maps = CrowdmapService.fromString(json, Maps.class);
        assertNotNull(maps);
        assertNotNullOrEmpty("Map is empty", maps.getMaps());
        assertEquals(2464, maps.getMaps().get(0).getId());
        assertEquals(3950, maps.getMaps().get(0).getUserId());
        assertEquals("private unaided schoolssouthdelhi", maps.getMaps().get(0).getName());
        assertEquals(
                "//b25c7ada827abcbc0630-5454a9e6f7100566866dd221e5013c79.ssl.cf2.rackcdn.com/522d4db6a37797.24377648_c.jpg",
                maps.getMaps().get(0).getAvatar());
        assertNull(maps.getMaps().get(0).getBanner());
        assertEquals("privateunaidedschoolssouthdelhi", maps.getMaps().get(0).getSubdomain());
        assertEquals(
                "mapping private unaided schools in South Delhi for flagging the schools which are entitled to give 25% seats to children of the disadvantaged class according to the Right to Education.",
                maps.getMaps().get(0).getDescription());
        assertEquals("collaborator", maps.getMaps().get(0).getModeration());
        assertNotNullOrEmpty("User list is empty", maps.getMaps().get(0).getUsers());
        assertEquals("neo", maps.getMaps().get(0).getUsers().get(0).getUsername());
        assertEquals("Debanshu Roy", maps.getMaps().get(0).getUsers().get(0).getName());
    }

    @Test
    public void testMapSettingsDeserialization() throws Exception {
        final String json = "{\n"
                + "    \"maps_settings\": [\n"
                + "        {\n"
                + "            \"maps_settings_id\": 395,\n"
                + "            \"map_id\": 1910,\n"
                + "            \"setting\": \"testsettingname\",\n"
                + "            \"value\": \"thevalueofthesettings\"\n"
                + "        }\n"
                + "    ],\n"
                + "    \"success\": true,\n"
                + "    \"status\": 200,\n"
                + "    \"timestamp\": 1375073093,\n"
                + "    \"qcount\": 8,\n"
                + "    \"elapsed\": \"0.2645s\"\n"
                + "}";
        MapSettings mapSettings = CrowdmapService.fromString(json, MapSettings.class);
        assertNotNull(mapSettings);
        assertNotNullOrEmpty("Map Settings is empty", mapSettings.getMapsSettings());
        assertEquals(395, mapSettings.getMapsSettings().get(0).getId());
        assertEquals(1910, mapSettings.getMapsSettings().get(0).getMapId());
        assertEquals("testsettingname", mapSettings.getMapsSettings().get(0).getSetting());
        assertEquals("thevalueofthesettings", mapSettings.getMapsSettings().get(0).getValue());
        assertEquals(true, mapSettings.isSuccess());
        assertEquals(200, mapSettings.getStatus());
        assertEquals(8, mapSettings.getQcount());
    }

    @Test
    public void testOwnerDeserialization() throws Exception {
        final String json = "{\n"
                + "\"owner\": {\n"
                + "\"user_id\": 50,\n"
                + "\"crowdmap_id\": \"rnveKhDBP4xlG2u05vXNDL3baQMATgEyJgcf2wdmg6JEjVFDvBC6MrrK6JKwmVyQg3CAy95iaMHKrWY7YneLJk7tfcyYZt1NCinhL7xvIif92dowlc54bMT4yJOpBXDa\",\n"
                + "\"crowdmap_id_h\": \"a7d72fe05700a9277a47052b8b668a56\",\n"
                + "\"username\": \"anushshetty\",\n"
                + "\"name\": \"Anush Shetty\",\n"
                + "\"bio\": \"\",\n"
                + "\"plus\": false,\n"
                + "\"baselayer\": \"crowdmap_satellite\",\n"
                + "\"instagram_auto_post\": false,\n"
                + "\"twitter_auto_post\": false,\n"
                + "\"twitter_auto_post_retweets\": false,\n"
                + "\"date_registered\": 1365426285,\n"
                + "\"banned\": \"0\"\n"
                + "},\n"
                + "\"success\": true,\n"
                + "\"status\": 200,\n"
                + "\"timestamp\": 1378705992,\n"
                + "\"qcount\": 3,\n"
                + "\"elapsed\": \"0.1147s\"\n"
                + "}";
        Owner owner = CrowdmapService.fromString(json, Owner.class);
        assertNotNull(owner);
        assertEquals(50, owner.getOwner().getId());
        assertEquals("anushshetty", owner.getOwner().getUsername());
        assertEquals("Anush Shetty", owner.getOwner().getName());
        assertEquals(200, owner.getStatus());
    }

    @Test
    public void testMapTagsDeserialization() throws Exception {
        final String json = "{\n"
                + "\"maps_tags\": [\n"
                + "{\n"
                + "\"tag\": \"books\",\n"
                + "\"users_count\": 1,\n"
                + "\"maps_count\": 1,\n"
                + "\"users\": [\n"
                + "3\n"
                + "],\n"
                + "\"maps\": [\n"
                + "2006\n"
                + "]\n"
                + "}\n"
                + "],\n"
                + "\"next\": \"https://api.crowdmap.com/v1/maps/tags/books/?apikey=godmode&limit=1&offset=1\",\n"
                + "\"curr\": \"https://api.crowdmap.com/v1/maps/tags/books/?apikey=godmode&limit=1\",\n"
                + "\"prev\": false,\n"
                + "\"success\": true,\n"
                + "\"status\": 200,\n"
                + "\"timestamp\": 1378707149,\n"
                + "\"qcount\": 3,\n"
                + "\"elapsed\": \"0.0864s\"\n"
                + "}";
        MapTags mapTags = CrowdmapService.fromString(json, MapTags.class);
        assertNotNull(mapTags);
        assertNotNullOrEmpty("Maps is empty", mapTags.getMapsTags());
        assertEquals("books", mapTags.getMapsTags().get(0).getTag());
        assertEquals(1, mapTags.getMapsTags().get(0).getUsersCount());
        assertEquals(1, mapTags.getMapsTags().get(0).getMapsCount());
        assertEquals(1, mapTags.getMapsTags().get(0).getUsers().length);
        assertEquals(3, mapTags.getMapsTags().get(0).getUsers()[0]);
        assertEquals(1, mapTags.getMapsTags().get(0).getMaps().length);
        assertEquals(2006, mapTags.getMapsTags().get(0).getMaps()[0]);
    }

    @Test
    public void testMapNotificationsDeserialization() throws Exception {
        final String json
                = "{\"count\":3,\"unread\":3,\"notifications\":[{\"id\":\"ac053c714940310d0a984765d5ee68c7\",\"sender\":3801,\"message\":\"Commune Elguettar (@commune2180) commented on your post.\",\"unread\":true,\"callback\":\"https:\\/\\/crowdmap.com\\/post\\/16770\\/\",\"created\":1378033388},{\"id\":\"8ffe56f51d61cf9b63c652eb76234d99\",\"sender\":2405,\"message\":\"Stefano Capezzuto (@sigisbaldo) liked your post.\",\"unread\":true,\"callback\":\"https:\\/\\/crowdmap.com\\/post\\/16446\\/\",\"created\":1377557141},{\"id\":\"cf6a18d7d123680a4dc10ae002f0c2ac\",\"sender\":57,\"message\":\"@heatherleson (@heatherleson) liked your post.\",\"unread\":true,\"callback\":\"https:\\/\\/crowdmap.com\\/post\\/16446\\/\",\"created\":1377529076}],\"next\":false,\"curr\":\"https:\\/\\/api.crowdmap.com\\/v1\\/users\\/me\\/notifications\\/?session=UKj0exdv9QLrT9KAqc67459ae6a7ceedcbe7e00a378718384cf94660d&_=1378708576682\",\"prev\":false,\"success\":true,\"status\":200,\"timestamp\":1378708616,\"qcount\":6,\"elapsed\":\"0.1631s\"}";
        Notifications notifications = CrowdmapService.fromString(json, Notifications.class);
        assertNotNull(notifications);
        assertNotNullOrEmpty("Notification is empty", notifications.getNotifications());
        assertEquals(3, notifications.getCount());
        assertEquals(3, notifications.getUnread());
        assertEquals("ac053c714940310d0a984765d5ee68c7",
                notifications.getNotifications().get(0).getId());
        assertEquals(3801, notifications.getNotifications().get(0).getSender());
        assertEquals("Commune Elguettar (@commune2180) commented on your post.",
                notifications.getNotifications().get(0).getMessage());
        assertEquals(true, notifications.getNotifications().get(0).isUnread());
        assertEquals(200, notifications.getStatus());
        assertEquals(6, notifications.getQcount());
    }

    @Test
    public void testMapNotificationsCountDeserialization() throws Exception {
        final String json
                = "{\"count\":3,\"unread\":3,\"success\":true,\"status\":200,\"timestamp\":1378708581,\"qcount\":6,\"elapsed\":\"0.1748s\"}";
        Notifications notifications = CrowdmapService.fromString(json, Notifications.class);
        assertNotNull(notifications);
        assertEquals(3, notifications.getCount());
        assertEquals(3, notifications.getUnread());
        assertEquals(200, notifications.getStatus());
        assertEquals(6, notifications.getQcount());
    }

    @Test
    public void testOEmbedDeserialization() throws Exception {
        final String json = "{\n"
                + "    \"version\": \"1.0\",\n"
                + "    \"type\": \"rich\",\n"
                + "    \"title\": \"Brian Herbert's Post on Crowdmap\",\n"
                + "    \"url\": \"https:\\/\\/crowdmap.com\\/post\\/832\\/saw-jonshuler-posting-this-on-facebook-check-out-the\\/\",\n"
                + "    \"author_name\": \"brianherbert\",\n"
                + "    \"author_url\": \"https:\\/\\/crowdmap.com\\/user\\/brianherbert\\/\",\n"
                + "    \"provider_name\": \"Crowdmap\",\n"
                + "    \"provider_url\": \"https:\\/\\/crowdmap.com\\/\",\n"
                + "    \"width\": \"560\",\n"
                + "    \"height\": \"550\",\n"
                + "    \"html\": \"<iframe width=\\\"560px\\\" height=\\\"500px\\\" src=\\\"https:\\/\\/crowdmap.com\\/post\\/832\\/saw-jonshuler-posting-this-on-facebook-check-out-the\\/\\\" frameborder=\\\"0\\\" scrolling=\\\"yes\\\"><\\/iframe>\"\n"
                + "}";
        OEmbed oEmbed = CrowdmapService.fromString(json, OEmbed.class);
        assertNotNull(oEmbed);
        assertEquals(1.0f, oEmbed.getVersion());
        assertEquals("Brian Herbert's Post on Crowdmap", oEmbed.getTitle());
        assertEquals(
                "https://crowdmap.com/post/832/saw-jonshuler-posting-this-on-facebook-check-out-the/",
                oEmbed.getUrl());
        assertEquals("brianherbert", oEmbed.getAuthorName());
        assertEquals("https://crowdmap.com/user/brianherbert/", oEmbed.getAuthorUrl());
        assertEquals("Crowdmap", oEmbed.getProviderName());
        assertEquals("https://crowdmap.com/", oEmbed.getProviderUrl());
        assertEquals(560, oEmbed.getWidth());
        assertEquals(550, oEmbed.getHeight());
        assertEquals(
                "<iframe width=\"560px\" height=\"500px\" src=\"https://crowdmap.com/post/832/saw-jonshuler-posting-this-on-facebook-check-out-the/\" frameborder=\"0\" scrolling=\"yes\"></iframe>",
                oEmbed.getHtml());
    }

    @Test
    public void testPostTagsDeserialization() throws Exception {
        String json = "{\n"
                + "\"posts_tags\": [\n"
                + "{\n"
                + "\"tag\": \"design\",\n"
                + "\"color\": \"C9C9C9\",\n"
                + "\"users_count\": 1,\n"
                + "\"posts_count\": 1,\n"
                + "\"users\": [\n"
                + "2\n"
                + "],\n"
                + "\"posts\": [\n"
                + "4164\n"
                + "]\n"
                + "}\n"
                + "],\n"
                + "\"success\": true,\n"
                + "\"status\": 200,\n"
                + "\"timestamp\": 1378713955,\n"
                + "\"qcount\": 3,\n"
                + "\"elapsed\": \"0.1147s\"\n"
                + "}";
        PostTags postTags = CrowdmapService.fromString(json, PostTags.class);
        assertNotNull(postTags);
        assertNotNullOrEmpty("Post tags empty", postTags.getPostsTags());
        assertEquals("design", postTags.getPostsTags().get(0).getTag());
        assertEquals("C9C9C9", postTags.getPostsTags().get(0).getColor());
        assertEquals(1, postTags.getPostsTags().get(0).getUsersCount());
        assertEquals(1, postTags.getPostsTags().get(0).getPostsCount());
        assertEquals(1, postTags.getPostsTags().get(0).getUsers().length);
        assertEquals(2, postTags.getPostsTags().get(0).getUsers()[0]);
        assertEquals(1, postTags.getPostsTags().get(0).getPosts().length);
        assertEquals(4164, postTags.getPostsTags().get(0).getPosts()[0]);
        assertEquals(true, postTags.isSuccess());
        assertEquals(200, postTags.getStatus());
        assertEquals(3, postTags.getQcount());
    }

    @Test
    public void testUsersDeserialization() throws Exception {

        final String json = "{\n"
                + "    \"users\": [\n"
                + "        {\n"
                + "            \"user_id\": 3293,\n"
                + "            \"crowdmap_id\": \"12Y3vkSlgLKC4cxm5atkFzoBz1EyjtziC0SybkJxOmzD6PGgAwCCzCCOGJHKVqnKq1xL3cfErjBdAR4czP0hfb1jimVMhksxhC3aWZRjvGaAcGiFvpxfeGFuWnWLHma3\",\n"
                + "            \"crowdmap_id_h\": \"f4d1f526e2fb3e10445d04737ce75faf\",\n"
                + "            \"username\": \"kjs\",\n"
                + "            \"name\": \"Kevin Skinner\",\n"
                + "            \"bio\": \"\",\n"
                + "            \"plus\": false,\n"
                + "            \"baselayer\": \"crowdmap_satellite\",\n"
                + "            \"instagram_auto_post\": false,\n"
                + "            \"twitter_auto_post\": false,\n"
                + "            \"twitter_auto_post_retweets\": false,\n"
                + "            \"date_registered\": 1375774449,\n"
                + "            \"banned\": false,\n"
                + "            \"avatar\": \"\\/\\/www.gravatar.com\\/avatar\\/0677578025aba7f73cd5dee14ae85c49?r=PG&s=200&d=404\",\n"
                + "            \"badges\": [\n"
                + "\n"
                + "            ]\n"
                + "        },\n"
                + "        {\n"
                + "            \"user_id\": 3292,\n"
                + "            \"crowdmap_id\": \"be56d53c3a259eef2f51f393f65a0a3a06205b8c7de24183d7db159116ed303476757a314a38386a8e9a7b46cefffa378435d5d333154940d681199cb0d6f53a\",\n"
                + "            \"crowdmap_id_h\": \"239314db89fb8d801c5faf8dff5c0b01\",\n"
                + "            \"username\": \"blont68\",\n"
                + "            \"name\": \"Bart Lont\",\n"
                + "            \"bio\": \"\",\n"
                + "            \"plus\": false,\n"
                + "            \"baselayer\": \"crowdmap_satellite\",\n"
                + "            \"instagram_auto_post\": false,\n"
                + "            \"twitter_auto_post\": false,\n"
                + "            \"twitter_auto_post_retweets\": false,\n"
                + "            \"date_registered\": 1375773229,\n"
                + "            \"banned\": false,\n"
                + "            \"avatar\": null,\n"
                + "            \"badges\": [\n"
                + "\n"
                + "            ]\n"
                + "        }\n"
                + "    ],\n"
                + "    \"next\": \"https:\\/\\/api.crowdmap.com\\/v1\\/users\\/?apikey=godmode&limit=2&offset=2\",\n"
                + "    \"curr\": \"https:\\/\\/api.crowdmap.com\\/v1\\/users\\/?apikey=godmode&limit=2\",\n"
                + "    \"prev\": false,\n"
                + "    \"success\": true,\n"
                + "    \"status\": 200,\n"
                + "    \"timestamp\": 1375775196,\n"
                + "    \"qcount\": 3,\n"
                + "    \"elapsed\": \"0.1575s\"\n"
                + "}";

        Users users = CrowdmapService.fromString(json, Users.class);
        assertNotNull(users);
        assertNotNullOrEmpty("Users list is empty", users.getUsers());
        assertEquals(3293, users.getUsers().get(0).getId());
        assertEquals("12Y3vkSlgLKC4cxm5atkFzoBz1EyjtziC0SybkJxOmzD6PGgAwCCzCCOGJHKVqnKq1xL3cfErjBdAR4czP0hfb1jimVMhksxhC3aWZRjvGaAcGiFvpxfeGFuWnWLHma3", users.getUsers().get(0).getCrowdmapId());
        assertEquals("f4d1f526e2fb3e10445d04737ce75faf",users.getUsers().get(0).getCrowdmapIdH());
        assertEquals("kjs", users.getUsers().get(0).getUsername());
        assertEquals("Kevin Skinner", users.getUsers().get(0).getName());
        assertEquals("", users.getUsers().get(0).getBio());
        assertEquals(false,users.getUsers().get(0).isPlus());
        assertEquals("crowdmap_satellite", users.getUsers().get(0).getBaselayer());
        assertEquals(false,users.getUsers().get(0).isInstagramAutoPost());
        assertEquals(false, users.getUsers().get(0).isTwitterAutoPost());
        assertEquals(false, users.getUsers().get(0).isTwitterAutoPostRetweets());
        assertEquals(false, users.getUsers().get(0).isBanned());
        assertEquals("//www.gravatar.com/avatar/0677578025aba7f73cd5dee14ae85c49?r=PG&s=200&d=404",users.getUsers().get(0).getAvatar());
        assertEquals(0,users.getUsers().get(0).getBadges().size());
    }

}
