/*
 *    Copyright (c) 2026, VRAI Labs and/or its affiliates. All rights reserved.
 *
 *    This software is licensed under the Apache License, Version 2.0 (the
 *    "License") as published by the Apache Software Foundation.
 *
 *    You may not use this file except in compliance with the License. You may
 *    obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 */

package io.supertokens.pluginInterface.auditlog;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AuditedResultTest {

    private static AuditLogEvent event() {
        return new AuditLogEvent("app", "public", "recipeUser", "primaryUser", "account_linking", "OK",
                null, null, 1000L, null);
    }

    @Test
    public void constructorRejectsNullHeadEvent() {
        try {
            new AuditedResult<>("result", (AuditLogEvent) null);
            fail("expected IllegalArgumentException for a zero-event audited result");
        } catch (IllegalArgumentException expected) {
            // the only zero-event path is withoutAudit(...)
        }
    }

    @Test
    public void constructorRejectsNullTailEvent() {
        try {
            new AuditedResult<>("result", event(), (AuditLogEvent) null);
            fail("expected IllegalArgumentException for a null tail event");
        } catch (IllegalArgumentException expected) {
            // events must not be null
        }
    }

    @Test
    public void carriesHeadAndTailEvents() {
        AuditLogEvent head = event();
        AuditLogEvent tail = event();
        AuditedResult<String> audited = new AuditedResult<>("result", head, tail);

        assertEquals("result", audited.getResult());
        assertTrue(audited.hasAudit());
        assertEquals(2, audited.getEvents().size());
        assertSame(head, audited.getEvents().get(0));
        assertSame(tail, audited.getEvents().get(1));
        assertNull(audited.getJustification());
    }

    @Test
    public void withoutAuditCarriesNoEvents() {
        AuditedResult<String> audited = AuditedResult.withoutAudit("result", "no user-visible mutation");

        assertEquals("result", audited.getResult());
        assertFalse(audited.hasAudit());
        assertTrue(audited.getEvents().isEmpty());
        assertEquals("no user-visible mutation", audited.getJustification());
    }

    @Test
    public void withoutAuditRequiresJustification() {
        try {
            AuditedResult.withoutAudit("result", "   ");
            fail("expected IllegalArgumentException for a blank justification");
        } catch (IllegalArgumentException expected) {
            // withoutAudit must document why no audit event is emitted
        }
    }

    @Test
    public void eventsListIsUnmodifiable() {
        AuditedResult<String> audited = new AuditedResult<>("result", event());
        try {
            audited.getEvents().add(event());
            fail("expected the events list to be unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // events are exposed read-only
        }
    }
}
