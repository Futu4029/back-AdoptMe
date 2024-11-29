package com.unq.adopt_me.util.algoritmpointscalculator;

import com.unq.adopt_me.dto.adoption.AdoptionResponse;
import com.unq.adopt_me.entity.user.User;
import com.unq.adopt_me.util.algoritmpointscalculator.pointscalculator.DistancePointsCalculator;
import com.unq.adopt_me.util.algoritmpointscalculator.pointscalculator.PetAgePointsCalculator;
import com.unq.adopt_me.util.algoritmpointscalculator.pointscalculator.PetSizePointsCalculator;
import com.unq.adopt_me.util.algoritmpointscalculator.pointscalculator.PointsCalculator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AdoptionPointsCalculator {
    private final List<PointsCalculator> calculators;

    public AdoptionPointsCalculator() {
        this.calculators = List.of(
                new DistancePointsCalculator(),
                new PetAgePointsCalculator(),
                new PetSizePointsCalculator()
        );
    }

    public void calculatePoints(AdoptionResponse adoptionResponse, User user) {
        int totalPoints = Arrays.stream(PointsPerAttribute.values())
                .map(attribute -> calculators.stream()
                        .filter(calculator -> calculator.canHandle(attribute))
                        .findFirst()
                        .map(calculator -> calculator.calculatePoints(adoptionResponse, user))
                        .orElse(0))
                .mapToInt(Integer::intValue)
                .sum();

        // Establece el total de puntos en AdoptionResponse
        adoptionResponse.setMatchPoints(totalPoints);
    }
}
