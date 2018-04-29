package com.learning.library.repository.data;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;

import com.learning.library.model.Category;

@Ignore
public class CategoryData {
    public static List<Category> allCategories() {
        return Arrays.asList(architecture(), cleanCode(), java(), kotlin());

    }

    public static Category architecture() {
        return new Category("architecture");
    }

    public static Category cleanCode() {
        return new Category("clean code");
    }

    public static Category java() {
        return new Category("java");
    }

    public static Category kotlin() {
        return new Category("kotlin");
    }
}
