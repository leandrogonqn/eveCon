package com.leanGomez.modules.simple.fixture.scenario;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import com.google.common.collect.Lists;
import com.leanGomez.modules.simple.dom.provincia.Provincia;
import com.leanGomez.modules.simple.dom.provincia.ProvinciaMenu;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class CreateProvincia extends FixtureScript{
	
    /**
     * The number of objects to create, up to 10; optional, defaults to 3.
     */
    @Nullable
    private Integer number;
    

    public Integer getNumber() {
		return number;
	}

	public CreateProvincia setNumber(Integer number) {
		this.number = number;
		return this;
	}

	/**
     * The objects created by this fixture (output).
     */
    @Getter
    private final List<Provincia> provincia = Lists.newArrayList();

	@Override
	protected void execute(final ExecutionContext ec) {
		// TODO Auto-generated method stub
        int max = ProvinciaData.values().length;

        // defaults
        final int number = defaultParam("number", ec, 3);

        // validate
        if(number < 0 || number > max) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d)", max));
        }

        // execute
        for (int i = 0; i < number; i++) {
            final ProvinciaData data = ProvinciaData.values()[i];
            final Provincia prov =  data.createWith(wrap(provinciaMenu)); 
            ec.addResult(this, prov);
            provincia.add(prov);
        }
	}
	
	@Inject
	ProvinciaMenu provinciaMenu;

}
