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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The outcome of a mutation that must be audited: the mutation's result plus the audit events that
 * describe it. It is the return type of {@link AuditableTransactionLogic}, and the combinator
 * {@code ActivityLogSQLStorage.startAuditedTransaction} writes the carried events on the same
 * connection as the mutation before committing.
 * <p>
 * The public constructor structurally requires at least one event (a head argument plus a varargs
 * tail), so "commit a mutation without an audit event" cannot happen by accident. The single
 * deliberate zero-event path is {@link #withoutAudit(Object, String)}, which demands a written
 * justification and is therefore greppable.
 */
public class AuditedResult<T> {
    private final T result;
    private final List<AuditLogEvent> events;
    private final String justification;

    /**
     * A result carrying one or more audit events. At least one event is required; pass additional
     * events as the varargs tail.
     *
     * @throws IllegalArgumentException if the head event or any tail event is null.
     */
    public AuditedResult(T result, AuditLogEvent event, AuditLogEvent... moreEvents) {
        if (event == null) {
            throw new IllegalArgumentException(
                    "An AuditedResult requires at least one non-null audit event; use "
                            + "AuditedResult.withoutAudit(result, justification) for the deliberate no-audit case.");
        }
        List<AuditLogEvent> collected = new ArrayList<>();
        collected.add(event);
        if (moreEvents != null) {
            for (AuditLogEvent extra : moreEvents) {
                if (extra == null) {
                    throw new IllegalArgumentException("Audit events must not be null.");
                }
                collected.add(extra);
            }
        }
        this.result = result;
        this.events = Collections.unmodifiableList(collected);
        this.justification = null;
    }

    private AuditedResult(T result, String justification) {
        this.result = result;
        this.events = Collections.emptyList();
        this.justification = justification;
    }

    /**
     * The only path that carries no audit event. Requires a non-empty justification documenting why
     * this mutation is intentionally exempt from auditing, so exemptions stay explicit and searchable.
     *
     * @throws IllegalArgumentException if the justification is null or blank.
     */
    public static <T> AuditedResult<T> withoutAudit(T result, String justification) {
        if (justification == null || justification.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "AuditedResult.withoutAudit requires a non-empty justification explaining why no audit "
                            + "event is emitted.");
        }
        return new AuditedResult<>(result, justification);
    }

    public T getResult() {
        return result;
    }

    /**
     * The audit events to persist for this mutation; empty only for {@link #withoutAudit}. Unmodifiable.
     */
    public List<AuditLogEvent> getEvents() {
        return events;
    }

    public boolean hasAudit() {
        return !events.isEmpty();
    }

    /**
     * The justification supplied to {@link #withoutAudit}, or null when this result carries events.
     */
    public String getJustification() {
        return justification;
    }
}
