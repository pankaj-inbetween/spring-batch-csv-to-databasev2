package com.bharathitech.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.bharathitech.dto.AddressDTO;
import com.bharathitech.model.AddressModel;

/**
 * The Class AddressProcessor.
 * <p>
 * Class description explaining the usage.
 * </p>
 *
 * @author Bharathidasan
 */
public class AddressProcessor implements ItemProcessor<AddressModel, AddressDTO> {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressProcessor.class);

    @Override
    public AddressDTO process(final AddressModel addressModel) throws Exception {

        LOGGER.info("Transforming AddressModel(s) to AddressDTO(s)..");

        final AddressDTO addressDTO = new AddressDTO(addressModel.getName(),addressModel.getAddress1(),addressModel.getAddress2(),
        		addressModel.getZipCode(),addressModel.getCity(),addressModel.getCountry());

        return addressDTO;
    }

}
