package com.marcom;

import com.marcom.Exception.MapperException;

/**
 * [ENG] Direct (S -> D) and inverse (D -> S) mapper.
 * [RU]  Прямой(S -> D) и обратный(D -> S) транслятор.
 * @param <S> Source, откуда копирует значения при транслировании.
 * @param <D> Destination, куда копирует значения при транслировании.
 * Created by Marolok.
 */
public class BiMapper<S, D> {
	private Mapper<S, D> mapper = new Mapper<>();
	private Mapper<D, S> remapper = new Mapper<>();

	/**
	 * [ENG] Direct (S -> D) mapper.
	 * [RU]  Прямой(S -> D) транслятор.
	 * @param source Source, откуда копирует значения при транслировании.
	 * @param destination Destination, куда копирует значения при транслировании.
	 * @param force пропускать не найденные аннотации, skip annotations not found.
	 * @throws MapperException
	 */
	public void translate(S source, D destination, boolean force) throws MapperException {
		mapper.translate( source, destination, force );
	}

	/**
	 * [ENG] Inverse (D -> S) mapper.
	 * [RU]  Обратный(D -> S) транслятор.
	 * @param source Source, откуда копирует значения при транслировании.
	 * @param destination Destination, куда копирует значения при транслировании.
	 * @param force пропускать не найденные аннотации, skip annotations not found.
	 * @throws MapperException
	 */
	public void translateBack(S source, D destination, boolean force) throws MapperException {
		remapper.translate( destination, source, force );
	}

}
