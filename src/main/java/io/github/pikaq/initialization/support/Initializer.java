package io.github.pikaq.initialization.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.reflections.Reflections;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Initializer {

	private static AtomicBoolean init = new AtomicBoolean(false);

	public static void init() {
		if (!init.compareAndSet(false, true)) {
			return;
		}
		Reflections packageInfo = new Reflections("io.github.pikaq.initialization.*");
		Set<Class<? extends Initable>> subs = packageInfo.getSubTypesOf(Initable.class);
		if (subs.isEmpty()) {
			return;
		}

		List<Initable> initables = Lists.newArrayList();
		for (Class<? extends Initable> clazz : subs) {
			if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())
					|| !Initable.class.isAssignableFrom(clazz)) {
				continue;
			}
			try {
				Constructor<? extends Initable> constructor = clazz.getDeclaredConstructor();
				constructor.setAccessible(true);
				Initable initable = clazz.newInstance();
				initables.add(initable);
			} catch (Throwable e) {
				log.warn("[Initializer] Init failed with fatal error", e);
	            throw new RuntimeException(e);
			}
		}

		log.info("[Initializer] Found Initable=[{}]", Arrays.asList(initables));

		Collections.sort(initables, new OrderComparator());

		for (Iterator<Initable> it = initables.iterator(); it.hasNext();) {
			Initable initable = (Initable) it.next();
			initable.init();
		}
	}

}
