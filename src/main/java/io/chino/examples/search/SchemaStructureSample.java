package io.chino.examples.search;

import io.chino.api.common.indexed;

import java.util.Date;

public class SchemaStructureSample {
    //Use this annotation if you want the targeted field indexed in the server
    @indexed
    public String test_string;
    @indexed
    public Integer test_integer;
    @indexed
    public Boolean test_boolean;
    @indexed
    public Date test_date;
}
