//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.25 at 12:42:49 PM CAT 
//


package com.ma2me.shortest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="shortestPathResponse" type="{http://ma2me.com/shortest}shortestPathResponse"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "shortestPathResponse"
})
@XmlRootElement(name = "GetShortestPathResponse")
public class GetShortestPathResponse {

    @XmlElement(required = true)
    protected ShortestPathResponse shortestPathResponse;

    /**
     * Gets the value of the shortestPathResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ShortestPathResponse }
     *     
     */
    public ShortestPathResponse getShortestPathResponse() {
        return shortestPathResponse;
    }

    /**
     * Sets the value of the shortestPathResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShortestPathResponse }
     *     
     */
    public void setShortestPathResponse(ShortestPathResponse value) {
        this.shortestPathResponse = value;
    }

}