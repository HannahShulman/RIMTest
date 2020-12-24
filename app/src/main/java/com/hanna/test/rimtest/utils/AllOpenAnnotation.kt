package com.hanna.test.rimtest.utils

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class AllOpenAnnotation

@AllOpenAnnotation
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting