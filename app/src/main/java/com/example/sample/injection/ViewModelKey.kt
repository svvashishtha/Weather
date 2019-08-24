package com.example.sample.injection

import androidx.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.Documented
import kotlin.reflect.KClass

@Suppress("DEPRECATED_JAVA_ANNOTATION")
@Documented
@Target(AnnotationTarget.FUNCTION)
@Retention
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)