/**
 *
 */
package com.bharathitech.dto;

import java.io.Serializable;


/**
 * The Class AddressDTO.
 * <p>
 * Class description explaining the usage.
 * </p>
 *
 * @author Bharathidasan
 */
public class AddressDTO implements Serializable {

    /** long holds the serialVersionUID. */
    private static final long serialVersionUID = 1L;

    private String name;
    
    private String address1;
    
    private String address2;
    
    private long zipCode;
    
    private String city;
    
    private String country;


 /**
  * @return the name
  */
 public String getName() {
 	return name;
 }

 /**
  * @return the address1
  */
 public String getAddress1() {
 	return address1;
 }

 /**
  * @return the address2
  */
 public String getAddress2() {
 	return address2;
 }

 /**
  * @return the zipCode
  */
 public long getZipCode() {
 	return zipCode;
 }

 /**
  * @return the city
  */
 public String getCity() {
 	return city;
 }

 /**
  * @return the country
  */
 public String getCountry() {
 	return country;
 }

 /**
  * @param name the name to set
  */
 public void setName(String name) {
 	this.name = name;
 }

 /**
  * @param address1 the address1 to set
  */
 public void setAddress1(String address1) {
 	this.address1 = address1;
 }

 /**
  * @param address2 the address2 to set
  */
 public void setAddress2(String address2) {
 	this.address2 = address2;
 }

 /**
  * @param zipCode the zipCode to set
  */
 public void setZipCode(long zipCode) {
 	this.zipCode = zipCode;
 }

 /**
  * @param city the city to set
  */
 public void setCity(String city) {
 	this.city = city;
 }

 /**
  * @param country the country to set
  */
 public void setCountry(String country) {
 	this.country = country;
 }

 /**
  * @param name
  * @param address1
  * @param address2
  * @param zipCode
  * @param city
  * @param country
  */
 public AddressDTO(String name, String address1, String address2, long zipCode, String city, String country) {
 	this.name = name;
 	this.address1 = address1;
 	this.address2 = address2;
 	this.zipCode = zipCode;
 	this.city = city;
 	this.country = country;
 }
 /**
  * 
  */
 public AddressDTO() {
 }

@Override
public String toString() {
	return "AddressDTO [name=" + name + ", address1=" + address1 + ", address2=" + address2 + ", zipCode=" + zipCode
			+ ", city=" + city + ", country=" + country + "]";
}

}
