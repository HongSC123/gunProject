package com.ict.gun.calorie.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNutritionFact is a Querydsl query type for NutritionFact
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNutritionFact extends EntityPathBase<NutritionFact> {

    private static final long serialVersionUID = -674091603L;

    public static final QNutritionFact nutritionFact = new QNutritionFact("nutritionFact");

    public final NumberPath<Float> calcium = createNumber("calcium", Float.class);

    public final NumberPath<Float> carbohydrate = createNumber("carbohydrate", Float.class);

    public final NumberPath<Float> cholesterol = createNumber("cholesterol", Float.class);

    public final NumberPath<Float> fat = createNumber("fat", Float.class);

    public final StringPath food_name = createString("food_name");

    public final NumberPath<Float> iron = createNumber("iron", Float.class);

    public final NumberPath<Float> kcal = createNumber("kcal", Float.class);

    public final NumberPath<Float> magnesium = createNumber("magnesium", Float.class);

    public final StringPath nutrition_num = createString("nutrition_num");

    public final NumberPath<Float> phosphorus = createNumber("phosphorus", Float.class);

    public final NumberPath<Float> potassium = createNumber("potassium", Float.class);

    public final NumberPath<Float> protein = createNumber("protein", Float.class);

    public final NumberPath<Float> sodium = createNumber("sodium", Float.class);

    public final NumberPath<Float> sugar = createNumber("sugar", Float.class);

    public final NumberPath<Float> trans_fat = createNumber("trans_fat", Float.class);

    public final NumberPath<Float> weight = createNumber("weight", Float.class);

    public final NumberPath<Float> zinc = createNumber("zinc", Float.class);

    public QNutritionFact(String variable) {
        super(NutritionFact.class, forVariable(variable));
    }

    public QNutritionFact(Path<? extends NutritionFact> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNutritionFact(PathMetadata metadata) {
        super(NutritionFact.class, metadata);
    }

}

