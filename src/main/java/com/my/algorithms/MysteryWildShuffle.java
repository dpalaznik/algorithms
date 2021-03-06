package com.my.algorithms;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.my.algorithms.tools.RandomUtils;
import com.my.algorithms.tools.Randoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MysteryWildShuffle {

    private static final double[] SCENARIO_W_MG_1 = {0.01, 0.24, 0.05, 0.7};
    private static final double[] SCENARIO_W_MG_2 = {0.7, 0.24, 0.05, 0.01};
    private static final double[] SCENARIO_W_MG_3 = {0, 0.9, 0.09, 0.01};

    private static final int[] MW_POSITIONS = {
            0, 1, 2, 3, 4,
            5, 6, 7, 8, 9,
            10, 11, 12, 13, 14};

    private static final Integer[][] MW_POSITIONS_ON_REELS = {
            {0, 5, 10},
            {1, 6, 11},
            {2, 7, 12},
            {3, 8, 13},
            {4, 9, 14}
    };
    private static final int NUM_REELS = 5;

    private static double[][] scenario = new double[][]{SCENARIO_W_MG_3, SCENARIO_W_MG_2, SCENARIO_W_MG_1, SCENARIO_W_MG_2, SCENARIO_W_MG_1};

    private static final Integer[][] ADJACENT_POSITIONS = {
            {1, 5, 6},
            {0, 2, 3, 6, 7},
            {1, 3, 6, 7, 8},
            {2, 4, 7, 8, 9},
            {3, 8, 9},
            {0, 1, 6, 10, 11},
            {0, 1, 2, 5, 7, 10, 11, 12},
            {1, 2, 3, 6, 8, 11, 12, 13},
            {2, 3, 4, 7, 9, 12, 13, 14},
            {3, 4, 8, 13, 14},
            {5, 6, 11},
            {5, 6, 7, 10, 12},
            {6, 7, 8, 11, 13},
            {7, 8, 9, 12, 14},
            {8, 9, 13},
    };

    public static void main(String[] args) {

        // generateMysteryWildsInFG(new int[]{3, 3, 3, 3, 3});
        // generateMysteryWildsInFG(new int[]{1, 0, 3, 1, 3});

        generateMysteryWildsInMG();
        generateMysteryWildsInFG();
    }

    private static void generateMysteryWildsInMG() {

        final int[] numberOfWildsByReels = getNumberOfWildsByReelsAsArray();
        final Integer[] mysteryWildsPositions = pickWildsPositions(numberOfWildsByReels);

        System.out.println("=== Mystery Wilds in Main Game ===\n");
        System.out.println("Number of MW on reels: " + Arrays.toString(numberOfWildsByReels));
        System.out.println("MW mystery wilds positions: " + Arrays.toString(mysteryWildsPositions));
        System.out.println("\n" + toString(mysteryWildsPositions));

        for (final int screenIndex : mysteryWildsPositions) {
            final int x = screenIndex % NUM_REELS;
            final int y = screenIndex / NUM_REELS;
            System.out.println(x + " : " + y);
        }

        System.out.println("\n=== Main Game END ===\n");
    }

    private static void generateMysteryWildsInFG() {
        generateMysteryWildsInFG(getNumberOfWildsByReelsAsArray());
    }

    private static void generateMysteryWildsInFG(final int[] numberOfWildsByReelsAsArray) {

        System.out.println("=== Mystery Wilds in Free Game ===\n");
        System.out.println("Number of MW on reels: " + Arrays.toString(numberOfWildsByReelsAsArray) + "\n");

        final Multimap<Integer, Integer> numberOfWildsByReels = getNumberOfWildsByReels(numberOfWildsByReelsAsArray);
        final Set<Integer> pickedWilds = new HashSet<Integer>(MW_POSITIONS.length);

        int iteration = 0;
        while (numberOfWildsByReels.size() > 0) {

            System.out.println("----- Iteration " + ++iteration + " -----\n");

            // Mystery wilds in array (for uniform pick)
            final Integer[] mysteryWilds = numberOfWildsByReels.values().toArray(new Integer[numberOfWildsByReels.size()]);
            System.out.println("Mystery wilds: " + numberOfWildsByReels.values());

            // Pick uniformly one random wild
            final int reelIndex = mysteryWilds[Randoms.randInt(0, mysteryWilds.length)];

            // Pick uniformly position on the reel
            final Set<Integer> wildsOnReel = new HashSet<Integer>(Arrays.asList(MW_POSITIONS_ON_REELS[reelIndex]));
            wildsOnReel.removeAll(pickedWilds);
            final int position = wildsOnReel.toArray(new Integer[wildsOnReel.size()])[Randoms.randInt(0, wildsOnReel.size())];
            pickedWilds.add(position);

            // Decrease number of numberOfWildsByReels
            decrementNumberOfWilds(numberOfWildsByReels, reelIndex);

            System.out.println("Pivot: " + position);
            System.out.println("Picked: " + pickedWilds + "\n");

            // Put, if possible, adjacent wilds
            final Integer[] adjacent = ADJACENT_POSITIONS[position];
            System.out.println("Adjacent: " + Arrays.toString(adjacent) + "\n");

            pickAdjacent(position, pickedWilds, numberOfWildsByReels);
        }
        System.out.println("=== Free Game END ===");
    }

    private static void decrementNumberOfWilds(final Multimap<Integer, Integer> numberOfWildsByReels, final int reelIndex) {
        final Iterator<Integer> iterator = numberOfWildsByReels.get(reelIndex).iterator();
        if (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    private static int[] getNumberOfWildsByReelsAsArray() {
        final int[] numberOfWildsByReels = new int[scenario.length];
        for (int i = 0; i < scenario.length; i++) {
            numberOfWildsByReels[i] = RandomUtils.randomIndexX(scenario[i]);
        }
        return numberOfWildsByReels;
    }

    private static Multimap<Integer, Integer> getNumberOfWildsByReels(final int[] numberOfWildsByReels) {
        final Multimap<Integer, Integer> numberOfWildsByReelsMap = ArrayListMultimap.create(5, 3);
        for (int reelIndex = 0; reelIndex < numberOfWildsByReels.length; reelIndex++) {
            if (numberOfWildsByReels[reelIndex] > 0) {
                numberOfWildsByReelsMap.putAll(reelIndex, Collections.nCopies(numberOfWildsByReels[reelIndex], reelIndex));
            }
        }
        return numberOfWildsByReelsMap;
    }

    private static Integer[] pickWildsPositions(final int[] numberOfWildsByReels) {
        final List<Integer> positions = new ArrayList<Integer>(MW_POSITIONS.length);
        for (int i = 0; i < scenario.length; i++) {
            final Integer[] positionsOnReel = Arrays.copyOfRange(RandomUtils.shuffle(MW_POSITIONS_ON_REELS[i]), 0, numberOfWildsByReels[i]);
            positions.addAll(Arrays.asList(positionsOnReel));
        }
        return positions.toArray(new Integer[positions.size()]);
    }

    private static void pickAdjacent(final int position, final Set<Integer> picked, final Multimap<Integer, Integer> numberOfWildsByReels) {

        final Set<Integer> adjacent = new HashSet<Integer>(Arrays.asList(ADJACENT_POSITIONS[position]));
        final int x = position % NUM_REELS;

        pickAdjacentOnReel(picked, adjacent, numberOfWildsByReels, x - 1);
        pickAdjacentOnReel(picked, adjacent, numberOfWildsByReels, x);
        pickAdjacentOnReel(picked, adjacent, numberOfWildsByReels, x + 1);

        System.out.println(toString(picked.toArray(new Integer[picked.size()]), position));
    }

    private static void pickAdjacentOnReel(final Set<Integer> picked, final Set<Integer> adjacent, final Multimap<Integer, Integer> numberOfWildsByReels, final int reelIndex) {
        if (reelIndex >= 0 && reelIndex < NUM_REELS) {
            final Set<Integer> pickFrom = new HashSet<Integer>(adjacent);
            pickFrom.retainAll(Arrays.asList(MW_POSITIONS_ON_REELS[reelIndex]));
            pickFrom.removeAll(picked);
            System.out.println("Adjacent on " + reelIndex + " reel: " + pickFrom);

            final Iterator<Integer> iterator = pickFrom.iterator();
            while (iterator.hasNext() && numberOfWildsByReels.get(reelIndex).size() > 0) {
                final Integer position = iterator.next();
                iterator.remove();
                decrementNumberOfWilds(numberOfWildsByReels, reelIndex);
                picked.add(position);
            }

            System.out.println("Picked after " + reelIndex + " : " + picked + "\n");
        }
    }

    private static String toString(final Integer[] wilds) {
        return toString(wilds, null);
    }

    private static String toString(final Integer[] wildsArray, final Integer pivot) {
        final Integer[] wilds = pivot != null ? removeFromArray(wildsArray, pivot) : wildsArray;
        Arrays.sort(wilds);
        int index = 0;
        int wildIndex = 0;
        final StringBuilder result = new StringBuilder(70);
        while (index < MW_POSITIONS.length) {
            result.append(' ');
            if (pivot != null && pivot == index) {
                result.append(" +");
            } else if (wildIndex < wilds.length && index == wilds[wildIndex]) {
                result.append(" -");
                wildIndex++;
            } else {
                result.append(String.format("%2d", index));
            }
            result.append(' ');
            if ((index + 1) % 5 == 0) {
                result.append("\n");
            }
            index++;
        }
        return result.toString();
    }

    private static Integer[] removeFromArray(Integer[] values, final int valueToDelete) {
        List<Integer> result = new ArrayList<Integer>(values.length);
        for (final int value : values) {
            if (value != valueToDelete) {
                result.add(value);
            }
        }
        return result.toArray(new Integer[result.size()]);
    }

}
