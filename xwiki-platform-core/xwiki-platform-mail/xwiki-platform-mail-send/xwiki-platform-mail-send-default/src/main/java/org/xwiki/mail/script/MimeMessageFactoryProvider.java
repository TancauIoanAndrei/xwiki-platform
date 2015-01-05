/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.mail.script;

import java.lang.reflect.Type;

import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.component.util.DefaultParameterizedType;
import org.xwiki.mail.MimeMessageFactory;
import org.xwiki.stability.Unstable;

/**
 * @version $Id$
 * @since 6.4M3
 */
@Unstable
public final class MimeMessageFactoryProvider
{
    private MimeMessageFactoryProvider()
    {
        //Hide the default constructor for utility classes
    }

    /**
     *
     * @param hint the component hint of a {@link org.xwiki.mail.MimeMessageFactory} component
     * @param type the type of the source from which to prefill the Mime Message
     * @param componentManager used to dynamically load all MimeMessageIterator
     * @return MimeMessage Factory
     * @throws ComponentLookupException when an error occurs
     */
    public static MimeMessageFactory get(String hint, Type type, ComponentManager componentManager)
        throws ComponentLookupException
    {
        MimeMessageFactory factory;
        // Step 1: Look for a secure version first
        try {
            factory = componentManager.getInstance(
                new DefaultParameterizedType(null, MimeMessageFactory.class, type),
                String.format("%s/secure", hint));
        } catch (ComponentLookupException e) {
            // Step 2: Look for a non secure version if a secure one doesn't exist...
            factory = componentManager.getInstance(
                new DefaultParameterizedType(null, MimeMessageFactory.class, type, null), hint);
        }

        return factory;
    }
}
