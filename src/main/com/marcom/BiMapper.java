package com.marcom;

import com.marcom.Exception.MapperException;

public class BiMapper<S, D> {
	private Mapper<S, D> mapper = new Mapper<>();
	private Mapper<D, S> remapper = new Mapper<>();

	public void translate(S source, D destination, boolean force) throws MapperException {
		mapper.translate( source, destination, force );
	}

	public void translateBack(S source, D destination, boolean force) throws MapperException {
		remapper.translate( destination, source, force );
	}

}
