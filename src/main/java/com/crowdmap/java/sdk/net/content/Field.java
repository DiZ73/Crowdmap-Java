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
package com.crowdmap.java.sdk.net.content;

/**
 * This class represents form fields.
 *
 * @author eyedol
 */
public class Field {

    /**
     * The name of the form field
     */
    private String name;

    /**
     * The value of the form field. Note the use of object
     */
    private Object value;

    public Field(String name, Object value) {

        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }

        this.name = name;
        this.value = value;
    }

    /**
     * Get the form field name
     *
     * @return the field name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the form field value
     *
     * @return the field value
     */
    public Object getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.name);
        buffer.append(": ");
        buffer.append(this.value.toString());
        return buffer.toString();
    }
}
