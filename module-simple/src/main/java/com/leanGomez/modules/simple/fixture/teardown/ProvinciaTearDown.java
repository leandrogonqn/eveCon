package com.leanGomez.modules.simple.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class ProvinciaTearDown extends FixtureScript{

    @Override
    protected void execute(ExecutionContext executionContext) {
//        isisJdoSupport.executeUpdate("delete from \"simple\".\"Marcas\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
