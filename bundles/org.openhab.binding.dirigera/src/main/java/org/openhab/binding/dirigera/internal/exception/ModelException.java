/*
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.dirigera.internal.exception;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * {@link ModelException} thrown in case of problems accessing Model
 *
 * @author Bernd Weymann - Initial contribution
 */
@NonNullByDefault
public class ModelException extends RuntimeException {

    private static final long serialVersionUID = -3080953131870014248L;

    public ModelException(String message) {
        super(message);
    }
}
