package com.chandara.phoneshop.mapper;

import com.chandara.phoneshop.DTO.ProductDTO;
import com.chandara.phoneshop.DTO.ProductImportDTO;
import com.chandara.phoneshop.Service.ColorService;
import com.chandara.phoneshop.Service.ModelService;
import com.chandara.phoneshop.entity.Product;
import com.chandara.phoneshop.entity.ProductImportHistories;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-08T21:04:03+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private ModelService modelService;
    @Autowired
    private ColorService colorService;
    private final DatatypeFactory datatypeFactory;

    public ProductMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public Product toProduct(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setModel( modelService.getById( productDTO.getModel_id() ) );
        product.setColor( colorService.getById( productDTO.getColor_id() ) );

        return product;
    }

    @Override
    public ProductImportHistories toProductImportHistories(ProductImportDTO productImportDTO, Product product) {
        if ( productImportDTO == null && product == null ) {
            return null;
        }

        ProductImportHistories productImportHistories = new ProductImportHistories();

        if ( productImportDTO != null ) {
            productImportHistories.setDateImport( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( productImportDTO.getImportDate() ) ) );
            productImportHistories.setImport_unit( productImportDTO.getProductUnit() );
            productImportHistories.setPricePerUnit( productImportDTO.getImportPrice() );
        }
        productImportHistories.setProduct( product );

        return productImportHistories;
    }

    private XMLGregorianCalendar localDateToXmlGregorianCalendar( LocalDate localDate ) {
        if ( localDate == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendarDate(
            localDate.getYear(),
            localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private static LocalDateTime xmlGregorianCalendarToLocalDateTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    Duration.ofMillis( xcal.getMillisecond() ).getNano()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }
}
