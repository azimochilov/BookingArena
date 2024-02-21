package com.booking.api.address;


import com.booking.domain.dtos.addresses.AddressCreationDto;
import com.booking.domain.dtos.addresses.AddressResultDto;
import com.booking.domain.dtos.addresses.AddressUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.booking.service.address.AddressService;
import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    //@PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid AddressCreationDto addressCreationDto){
        var address = addressService.create(addressCreationDto);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        AddressResultDto address = addressService.getById(id);

        return ResponseEntity.ok(address);
    }
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody AddressUpdateDto addressUpdateDto){
        AddressResultDto updatedAddress = addressService.update(id,addressUpdateDto);
        return ResponseEntity.ok(updatedAddress);
    }
}

