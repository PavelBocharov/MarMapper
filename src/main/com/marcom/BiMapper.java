package com.marcom;

import java.lang.reflect.InvocationTargetException;

import com.marcom.Exception.MapperException;

/**
 * [ENG] Direct (S to D) and inverse (D to S) mapper.
 * [RU]  Прямой(S to D) и обратный(D to S) транслятор.
 * @param <S> Source, откуда копирует значения при транслировании.
 * @param <D> Destination, куда копирует значения при транслировании.
 * Created by Marolok.
 */
public class BiMapper<S, D> {
	private Mapper<S, D> mapper = new Mapper<>();
	private Mapper<D, S> remapper = new Mapper<>();

	/**
	 * [ENG] Direct (S to D) mapper.
	 * [RU]  Прямой(S to D) транслятор.
	 * @param source Source, откуда копирует значения при транслировании.
	 * @param destination Destination, куда копирует значения при транслировании.
	 * @param force пропускать не найденные аннотации, skip annotations not found.
	 * @throws MapperException см {@link MapperException}
	 * @throws InvocationTargetException см {@link InvocationTargetException}
	 * @throws IllegalAccessException см {@link IllegalAccessException}
	 */
	public void translate(S source, D destination, boolean force)
			throws MapperException, InvocationTargetException, IllegalAccessException {
		mapper.translate( source, destination, force );
	}

	/**
	 * [ENG] Inverse (D to S) mapper.
	 * [RU]  Обратный(D to S) транслятор.
	 * @param source Source, откуда копирует значения при транслировании.
	 * @param destination Destination, куда копирует значения при транслировании.
	 * @param force пропускать не найденные аннотации, skip annotations not found.
	 * @throws MapperException см {@link MapperException}
	 * @throws InvocationTargetException см {@link InvocationTargetException}
	 * @throws IllegalAccessException см {@link IllegalAccessException}
	 */
	public void translateBack(S source, D destination, boolean force)
			throws MapperException, InvocationTargetException, IllegalAccessException {
		remapper.translate( destination, source, force );
	}

}
