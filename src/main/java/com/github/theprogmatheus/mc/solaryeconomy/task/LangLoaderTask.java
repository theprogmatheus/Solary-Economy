package com.github.theprogmatheus.mc.solaryeconomy.task;

import com.github.theprogmatheus.mc.solaryeconomy.config.Config;
import com.github.theprogmatheus.mc.solaryeconomy.config.Lang;
import com.github.theprogmatheus.mc.solaryeconomy.exception.LangConfigurationLoadException;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class LangLoaderTask implements Consumer<Config> {

    @Override
    public void accept(Config config) {
        // parse lang to langclass
        for (Field field : Lang.class.getDeclaredFields()) {
            String value = config.getString(field.getName());
            try {
                field.set(null, value);
            } catch (IllegalAccessException e) {
                throw new LangConfigurationLoadException("Cant set lang class field value: " + field.getName() + "=" + value, e);
            }
        }
    }
}
