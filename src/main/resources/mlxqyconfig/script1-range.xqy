xquery version "1.0-ml";
import module namespace admin = "http://marklogic.com/xdmp/admin" 
    at "/MarkLogic/admin.xqy";
let $config := admin:get-configuration()
let $dbid := xdmp:database("Documents")
let $validStart := admin:database-range-element-index("dateTime",
"", "validStart", "", fn:false() )
let $validEnd := admin:database-range-element-index("dateTime",
"", "validEnd", "", fn:false() )
let $systemStart := admin:database-range-element-index("dateTime",
"", "systemStart", "", fn:false() )
let $systemEnd := admin:database-range-element-index("dateTime",
"", "systemEnd", "", fn:false() )
let $config := admin:database-add-range-element-index($config, $dbid, $validStart)
let $config := admin:database-add-range-element-index($config, $dbid, $validEnd)
let $config := admin:database-add-range-element-index($config, $dbid, $systemStart)
let $config := admin:database-add-range-element-index($config, $dbid, $systemEnd)
return
    admin:save-configuration($config)
