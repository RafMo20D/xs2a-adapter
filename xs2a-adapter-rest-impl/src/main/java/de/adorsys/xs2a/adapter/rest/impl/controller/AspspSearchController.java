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

package de.adorsys.xs2a.adapter.rest.impl.controller;

import de.adorsys.xs2a.adapter.api.AspspReadOnlyRepository;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.mapper.AspspMapper;
import de.adorsys.xs2a.adapter.rest.api.AspspSearchApi;
import de.adorsys.xs2a.adapter.rest.api.model.AspspTO;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AspspSearchController implements AspspSearchApi {
    private final AspspReadOnlyRepository aspspSearchService;
    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    public AspspSearchController(AspspReadOnlyRepository aspspSearchService) {
        this.aspspSearchService = aspspSearchService;
    }

    @Override
    public ResponseEntity<List<AspspTO>> getAspsps(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String bic,
                                                   @RequestParam(required = false) String bankCode,
                                                   @RequestParam(required = false) String iban, // if present - other params ignored
                                                   @RequestParam(required = false) String after,
                                                   @RequestParam(required = false, defaultValue = "10") int size) {

        List<Aspsp> aspsps;
        if (iban != null) {
            aspsps = aspspSearchService.findByIban(iban, after, size);
        } else if (name == null && bic == null && bankCode == null) {
            aspsps = aspspSearchService.findAll(after, size);
        } else {
            Aspsp aspsp = new Aspsp();
            aspsp.setName(name);
            aspsp.setBic(bic);
            aspsp.setBankCode(bankCode);
            aspsps = aspspSearchService.findLike(aspsp, after, size);
        }

        return ResponseEntity.ok(aspspMapper.toAspspTOs(aspsps));
    }

    @Override
    public ResponseEntity<AspspTO> getById(String id) {
        Optional<Aspsp> aspsp = aspspSearchService.findById(id);
        return aspsp.map(value -> ResponseEntity.ok(aspspMapper.toAspspTO(value)))
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
