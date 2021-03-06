/*
 * XML Type:  timezonedefinition
 * Namespace: http://schemas.microsoft.com/crm/2007/WebServices
 * Java type: com.microsoft.schemas.crm._2007.webservices.Timezonedefinition
 *
 * Automatically generated - do not modify.
 */
package com.microsoft.schemas.crm._2007.webservices;


/**
 * An XML timezonedefinition(@http://schemas.microsoft.com/crm/2007/WebServices).
 *
 * This is a complex type.
 */
public interface Timezonedefinition extends com.microsoft.schemas.crm._2006.webservices.BusinessEntity
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Timezonedefinition.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sE3DFDC56E75679F2AF264CA469AD5996").resolveHandle("timezonedefinition270atype");
    
    /**
     * Gets the "bias" element
     */
    com.microsoft.schemas.crm._2006.webservices.CrmNumber getBias();
    
    /**
     * True if has "bias" element
     */
    boolean isSetBias();
    
    /**
     * Sets the "bias" element
     */
    void setBias(com.microsoft.schemas.crm._2006.webservices.CrmNumber bias);
    
    /**
     * Appends and returns a new empty "bias" element
     */
    com.microsoft.schemas.crm._2006.webservices.CrmNumber addNewBias();
    
    /**
     * Unsets the "bias" element
     */
    void unsetBias();
    
    /**
     * Gets the "createdby" element
     */
    com.microsoft.schemas.crm._2006.webservices.Lookup getCreatedby();
    
    /**
     * True if has "createdby" element
     */
    boolean isSetCreatedby();
    
    /**
     * Sets the "createdby" element
     */
    void setCreatedby(com.microsoft.schemas.crm._2006.webservices.Lookup createdby);
    
    /**
     * Appends and returns a new empty "createdby" element
     */
    com.microsoft.schemas.crm._2006.webservices.Lookup addNewCreatedby();
    
    /**
     * Unsets the "createdby" element
     */
    void unsetCreatedby();
    
    /**
     * Gets the "createdon" element
     */
    com.microsoft.schemas.crm._2006.webservices.CrmDateTime getCreatedon();
    
    /**
     * True if has "createdon" element
     */
    boolean isSetCreatedon();
    
    /**
     * Sets the "createdon" element
     */
    void setCreatedon(com.microsoft.schemas.crm._2006.webservices.CrmDateTime createdon);
    
    /**
     * Appends and returns a new empty "createdon" element
     */
    com.microsoft.schemas.crm._2006.webservices.CrmDateTime addNewCreatedon();
    
    /**
     * Unsets the "createdon" element
     */
    void unsetCreatedon();
    
    /**
     * Gets the "daylightname" element
     */
    java.lang.String getDaylightname();
    
    /**
     * Gets (as xml) the "daylightname" element
     */
    org.apache.xmlbeans.XmlString xgetDaylightname();
    
    /**
     * True if has "daylightname" element
     */
    boolean isSetDaylightname();
    
    /**
     * Sets the "daylightname" element
     */
    void setDaylightname(java.lang.String daylightname);
    
    /**
     * Sets (as xml) the "daylightname" element
     */
    void xsetDaylightname(org.apache.xmlbeans.XmlString daylightname);
    
    /**
     * Unsets the "daylightname" element
     */
    void unsetDaylightname();
    
    /**
     * Gets the "modifiedby" element
     */
    com.microsoft.schemas.crm._2006.webservices.Lookup getModifiedby();
    
    /**
     * True if has "modifiedby" element
     */
    boolean isSetModifiedby();
    
    /**
     * Sets the "modifiedby" element
     */
    void setModifiedby(com.microsoft.schemas.crm._2006.webservices.Lookup modifiedby);
    
    /**
     * Appends and returns a new empty "modifiedby" element
     */
    com.microsoft.schemas.crm._2006.webservices.Lookup addNewModifiedby();
    
    /**
     * Unsets the "modifiedby" element
     */
    void unsetModifiedby();
    
    /**
     * Gets the "modifiedon" element
     */
    com.microsoft.schemas.crm._2006.webservices.CrmDateTime getModifiedon();
    
    /**
     * True if has "modifiedon" element
     */
    boolean isSetModifiedon();
    
    /**
     * Sets the "modifiedon" element
     */
    void setModifiedon(com.microsoft.schemas.crm._2006.webservices.CrmDateTime modifiedon);
    
    /**
     * Appends and returns a new empty "modifiedon" element
     */
    com.microsoft.schemas.crm._2006.webservices.CrmDateTime addNewModifiedon();
    
    /**
     * Unsets the "modifiedon" element
     */
    void unsetModifiedon();
    
    /**
     * Gets the "organizationid" element
     */
    com.microsoft.schemas.crm._2006.webservices.Lookup getOrganizationid();
    
    /**
     * True if has "organizationid" element
     */
    boolean isSetOrganizationid();
    
    /**
     * Sets the "organizationid" element
     */
    void setOrganizationid(com.microsoft.schemas.crm._2006.webservices.Lookup organizationid);
    
    /**
     * Appends and returns a new empty "organizationid" element
     */
    com.microsoft.schemas.crm._2006.webservices.Lookup addNewOrganizationid();
    
    /**
     * Unsets the "organizationid" element
     */
    void unsetOrganizationid();
    
    /**
     * Gets the "retiredorder" element
     */
    com.microsoft.schemas.crm._2006.webservices.CrmNumber getRetiredorder();
    
    /**
     * True if has "retiredorder" element
     */
    boolean isSetRetiredorder();
    
    /**
     * Sets the "retiredorder" element
     */
    void setRetiredorder(com.microsoft.schemas.crm._2006.webservices.CrmNumber retiredorder);
    
    /**
     * Appends and returns a new empty "retiredorder" element
     */
    com.microsoft.schemas.crm._2006.webservices.CrmNumber addNewRetiredorder();
    
    /**
     * Unsets the "retiredorder" element
     */
    void unsetRetiredorder();
    
    /**
     * Gets the "standardname" element
     */
    java.lang.String getStandardname();
    
    /**
     * Gets (as xml) the "standardname" element
     */
    org.apache.xmlbeans.XmlString xgetStandardname();
    
    /**
     * True if has "standardname" element
     */
    boolean isSetStandardname();
    
    /**
     * Sets the "standardname" element
     */
    void setStandardname(java.lang.String standardname);
    
    /**
     * Sets (as xml) the "standardname" element
     */
    void xsetStandardname(org.apache.xmlbeans.XmlString standardname);
    
    /**
     * Unsets the "standardname" element
     */
    void unsetStandardname();
    
    /**
     * Gets the "timezonecode" element
     */
    com.microsoft.schemas.crm._2006.webservices.CrmNumber getTimezonecode();
    
    /**
     * True if has "timezonecode" element
     */
    boolean isSetTimezonecode();
    
    /**
     * Sets the "timezonecode" element
     */
    void setTimezonecode(com.microsoft.schemas.crm._2006.webservices.CrmNumber timezonecode);
    
    /**
     * Appends and returns a new empty "timezonecode" element
     */
    com.microsoft.schemas.crm._2006.webservices.CrmNumber addNewTimezonecode();
    
    /**
     * Unsets the "timezonecode" element
     */
    void unsetTimezonecode();
    
    /**
     * Gets the "timezonedefinitionid" element
     */
    com.microsoft.schemas.crm._2006.webservices.Key getTimezonedefinitionid();
    
    /**
     * True if has "timezonedefinitionid" element
     */
    boolean isSetTimezonedefinitionid();
    
    /**
     * Sets the "timezonedefinitionid" element
     */
    void setTimezonedefinitionid(com.microsoft.schemas.crm._2006.webservices.Key timezonedefinitionid);
    
    /**
     * Appends and returns a new empty "timezonedefinitionid" element
     */
    com.microsoft.schemas.crm._2006.webservices.Key addNewTimezonedefinitionid();
    
    /**
     * Unsets the "timezonedefinitionid" element
     */
    void unsetTimezonedefinitionid();
    
    /**
     * Gets the "userinterfacename" element
     */
    java.lang.String getUserinterfacename();
    
    /**
     * Gets (as xml) the "userinterfacename" element
     */
    org.apache.xmlbeans.XmlString xgetUserinterfacename();
    
    /**
     * True if has "userinterfacename" element
     */
    boolean isSetUserinterfacename();
    
    /**
     * Sets the "userinterfacename" element
     */
    void setUserinterfacename(java.lang.String userinterfacename);
    
    /**
     * Sets (as xml) the "userinterfacename" element
     */
    void xsetUserinterfacename(org.apache.xmlbeans.XmlString userinterfacename);
    
    /**
     * Unsets the "userinterfacename" element
     */
    void unsetUserinterfacename();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition newInstance() {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.microsoft.schemas.crm._2007.webservices.Timezonedefinition parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.microsoft.schemas.crm._2007.webservices.Timezonedefinition) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
