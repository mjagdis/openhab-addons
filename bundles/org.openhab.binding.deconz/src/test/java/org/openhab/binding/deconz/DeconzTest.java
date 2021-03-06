/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
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
package org.openhab.binding.deconz;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.openhab.binding.deconz.internal.Util;
import org.openhab.binding.deconz.internal.discovery.ThingDiscoveryService;
import org.openhab.binding.deconz.internal.dto.BridgeFullState;
import org.openhab.binding.deconz.internal.handler.DeconzBridgeHandler;
import org.openhab.binding.deconz.internal.types.*;
import org.openhab.core.config.discovery.DiscoveryListener;
import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ThingUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class provides tests for deconz binding
 *
 * @author Jan N. Klug - Initial contribution
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
@NonNullByDefault
public class DeconzTest {
    private @NonNullByDefault({}) Gson gson;

    private @Mock @NonNullByDefault({}) DiscoveryListener discoveryListener;
    private @Mock @NonNullByDefault({}) DeconzBridgeHandler bridgeHandler;
    private @Mock @NonNullByDefault({}) Bridge bridge;

    @BeforeEach
    public void initialize() {
        Mockito.doAnswer(answer -> bridge).when(bridgeHandler).getThing();
        Mockito.doAnswer(answer -> new ThingUID("deconz", "mybridge")).when(bridge).getUID();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LightType.class, new LightTypeDeserializer());
        gsonBuilder.registerTypeAdapter(GroupType.class, new GroupTypeDeserializer());
        gsonBuilder.registerTypeAdapter(ResourceType.class, new ResourceTypeDeserializer());
        gsonBuilder.registerTypeAdapter(ThermostatMode.class, new ThermostatModeGsonTypeAdapter());
        gson = gsonBuilder.create();
    }

    @Test
    public void discoveryTest() throws IOException {
        BridgeFullState bridgeFullState = getObjectFromJson("discovery.json", BridgeFullState.class, gson);
        assertNotNull(bridgeFullState);
        assertEquals(6, bridgeFullState.lights.size());
        assertEquals(9, bridgeFullState.sensors.size());

        ThingDiscoveryService discoveryService = new ThingDiscoveryService();
        discoveryService.setThingHandler(bridgeHandler);
        discoveryService.addDiscoveryListener(discoveryListener);

        discoveryService.stateRequestFinished(bridgeFullState);
        Mockito.verify(discoveryListener, times(20)).thingDiscovered(any(), any());
    }

    public static <T> T getObjectFromJson(String filename, Class<T> clazz, Gson gson) throws IOException {
        String json = new String(DeconzTest.class.getResourceAsStream(filename).readAllBytes(), StandardCharsets.UTF_8);
        return gson.fromJson(json, clazz);
    }

    @Test
    public void dateTimeConversionTest() {
        DateTimeType dateTime = Util.convertTimestampToDateTime("2020-08-22T11:09Z");
        assertEquals(new DateTimeType(ZonedDateTime.parse("2020-08-22T11:09:00Z")), dateTime);

        dateTime = Util.convertTimestampToDateTime("2020-08-22T11:09:47");
        assertEquals(new DateTimeType(ZonedDateTime.parse("2020-08-22T11:09:47Z")).toZone(ZoneId.systemDefault()),
                dateTime);
    }
}
