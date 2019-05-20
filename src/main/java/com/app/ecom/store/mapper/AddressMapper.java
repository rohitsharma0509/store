package com.app.ecom.store.mapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.app.ecom.store.dto.AddressDto;
import com.app.ecom.store.model.Address;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AddressMapper {

	public Set<Address> addressDtosToAddress(Set<AddressDto> addressDtos) {
		if(CollectionUtils.isEmpty(addressDtos)) {
			return Collections.emptySet();
		}
		
		Set<Address> addresses = new HashSet<>();
		addressDtos.stream().filter(Objects::nonNull).forEach(addressDto -> addresses.add(addressDtoToAddress(addressDto)));
		return addresses;
	}
	
	public Address addressDtoToAddress(AddressDto addressDto) {
		Address address = new Address();
		address.setId(addressDto.getId());
		address.setAddressLine1(addressDto.getAddressLine1());
		address.setAddressLine2(addressDto.getAddressLine2());
		address.setCity(addressDto.getCity());
		address.setPincode(addressDto.getPincode());
		address.setState(addressDto.getState());
		address.setCountry(addressDto.getCountry());
		address.setIsPrimary(addressDto.getIsPrimary());
		return address;
	}
	
	public Set<AddressDto> addressesToAddressDtos(Set<Address> addresses) {
		if(CollectionUtils.isEmpty(addresses)) {
			return Collections.emptySet();
		}
		
		Set<AddressDto> addressDtos = new HashSet<>();
		addresses.stream().filter(Objects::nonNull).forEach(address -> addressDtos.add(addressToAddressDto(address)));
		return addressDtos;
	}
	
	public AddressDto addressToAddressDto(Address address) {
		AddressDto addressDto = new AddressDto();
		addressDto.setId(address.getId());
		addressDto.setAddressLine1(address.getAddressLine1());
		addressDto.setAddressLine2(address.getAddressLine2());
		addressDto.setCity(address.getCity());
		addressDto.setPincode(address.getPincode());
		addressDto.setState(address.getState());
		addressDto.setCountry(address.getCountry());
		addressDto.setIsPrimary(address.getIsPrimary());
		return addressDto;
	}

}
