//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.06 at 02:20:44 PM EEST 
//


package org.jmt.util.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfigurationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigurationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="jmt" type="{http://www.example.org/jmt/}JmtType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigurationType", propOrder = {
    "jmt"
})
public class ConfigurationType {

    @XmlElement(required = true)
    protected JmtType jmt;

    /**
     * Gets the value of the jmt property.
     * 
     * @return
     *     possible object is
     *     {@link JmtType }
     *     
     */
    public JmtType getJmt() {
        return jmt;
    }

    /**
     * Sets the value of the jmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JmtType }
     *     
     */
    public void setJmt(JmtType value) {
        this.jmt = value;
    }

}
