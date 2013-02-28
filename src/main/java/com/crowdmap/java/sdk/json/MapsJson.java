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
package com.crowdmap.java.sdk.json;

import java.io.Serializable;
import java.util.List;

import com.crowdmap.java.sdk.model.Maps;

/**
 * Maps response model class
 */
public class MapsJson extends ResponseJson implements Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 6835878593630432210L;
	
	public List<Maps> maps;


}
