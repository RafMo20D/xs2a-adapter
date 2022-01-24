/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.api.PkceOauth2Extension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PkceOauth2ExtensionTest {

    private final PkceExample pkceExample = new PkceExample();

    // https://tools.ietf.org/html/rfc7636#appendix-B
    static class PkceExample implements PkceOauth2Extension {
        @Override
        public byte[] octetSequence() {
            return new byte[]{116, 24, (byte) 223, (byte) 180, (byte) 151, (byte) 153, (byte) 224, 37, 79, (byte) 250, 96,
                125, (byte) 216, (byte) 173, (byte) 187, (byte) 186, 22, (byte) 212, 37, 77, 105, (byte) 214, (byte) 191,
                (byte) 240, 91, 88, 5, 88, 83, (byte) 132, (byte) 141, 121};
        }
    }

    @Test
    void codeVerifier() {
        assertEquals("dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk", pkceExample.codeVerifier());
    }

    @Test
    void codeChallenge() {
        assertEquals("E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM", pkceExample.codeChallenge());
    }
}
