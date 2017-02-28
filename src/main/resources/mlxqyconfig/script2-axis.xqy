xquery version "1.0-ml"; 
import module namespace temporal = "http://marklogic.com/xdmp/temporal" 
    at "/MarkLogic/temporal.xqy";
temporal:axis-create(
    "valid",
    cts:element-reference(xs:QName("validStart")),
    cts:element-reference(xs:QName("validEnd")));
xquery version "1.0-ml"; 
import module namespace temporal = "http://marklogic.com/xdmp/temporal" 
    at "/MarkLogic/temporal.xqy";
temporal:axis-create(
    "system",
    cts:element-reference(xs:QName("systemStart")),
    cts:element-reference(xs:QName("systemEnd")));
  