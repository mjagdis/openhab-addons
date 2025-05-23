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
package org.openhab.binding.tesla.internal.protocol.dto.sso;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link TokenExchangeRequest} is a datastructure to exchange
 * the access token from the SSO endpoint for an owners API access token
 *
 * @author Christian Güdel - Initial contribution
 */
public class TokenExchangeRequest {
    @SerializedName("grant_type")
    public String grantType = "urn:ietf:params:oauth:grant-type:jwt-bearer";
    @SerializedName("client_id")
    public String clientId = "81527cff06843c8634fdc09e8ac0abefb46ac849f38fe1e431c2ef2106796384";
    @SerializedName("client_secret")
    public String clientSecret = "c7257eb71a564034f9419ee651c7d0e5f7aa6bfbd18bafb5c5c033b093bb2fa3";
}
