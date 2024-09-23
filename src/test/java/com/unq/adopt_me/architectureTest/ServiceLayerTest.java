package com.unq.adopt_me.architectureTest;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@SpringBootTest
public class ServiceLayerTest {
    private JavaClasses baseClasses;
    @BeforeEach
    void setup(){
        baseClasses = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.unq.adopt_me");
    }

    // ------- GENERAL ARCH TEST -----------//
    @Test
    void layers_architecture_test(){
        layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                .layer("Dao").definedBy("..dao..")

                .whereLayer("Controller").mayNotAccessAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Dao").mayOnlyBeAccessedByLayers("Service");
    }
    // ------- SERVICE ARCH TEST -----------//
    @Test
    void should_have_impl_name_test(){
        classes().that().resideInAPackage("..service.impl..")
                .should().haveSimpleNameEndingWith("Impl").check(baseClasses);
    }

    @Test
    void have_annotation_transactional_test(){
        classes().that().resideInAPackage("..service.impl..")
                .or().resideInAPackage("..initializer..")
                .should().beAnnotatedWith(Transactional.class)
                .andShould().beAnnotatedWith(Service.class)
                .check(baseClasses);
    }


    @Test
    void attribute_from_generic_service_test(){
        classes().that().resideInAPackage("..service.impl..")
                .should(new ArchCondition<JavaClass>("tener atributo terminado en Dao y está anotado con @Autowired") {
                    @Override
                    public void check(JavaClass javaClass, ConditionEvents events) {
                        boolean hasDaoAttributeAndHasAutowiredAnnotation = false;
                        for (JavaField field : javaClass.getFields()) {
                            if (field.getName().endsWith("Dao") && field.isAnnotatedWith(Autowired.class)) {
                                hasDaoAttributeAndHasAutowiredAnnotation = true;
                                break;
                            }
                        }
                        if (!hasDaoAttributeAndHasAutowiredAnnotation) {
                            events.add(SimpleConditionEvent.violated(javaClass,
                                    "La clase no tiene un atributo terminado en Dao y tiene la anotación @Autowired"));
                        }
                    }
                })
                .check(baseClasses);
    }

    // ------- CONTROLLER ARCH TEST -----------//

    @Test
    void controllers_are_called_by_controllers_test(){
        classes().that().resideInAPackage("..controller..")
                .should().haveSimpleNameEndingWith("Controller").check(baseClasses);
    }

}
