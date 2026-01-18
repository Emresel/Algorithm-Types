import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class CreditCard {
    String cardNumber;
    int expirationDate;
    int pin;
    
    public CreditCard(String cardNumber, int expirationDate, int pin) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.pin = pin;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return expirationDate == that.expirationDate && pin == that.pin;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(expirationDate, pin);
    }
    
    @Override
    public String toString() {
        return "Card{" + cardNumber + ", Exp:" + expirationDate + ", PIN:" + pin + "}";
    }
}

public class assignment1task1partB {
    
    public static List<CreditCard[]> matchLinear(List<CreditCard> part1, List<CreditCard> part2) {
        Map<String, List<CreditCard>> part2Map = new HashMap<>();
        for (CreditCard card : part2) {
            String key = card.expirationDate + "_" + card.pin;
            part2Map.computeIfAbsent(key, k -> new ArrayList<>()).add(card);
        }
        
        List<CreditCard[]> matches = new ArrayList<>();
        for (CreditCard card1 : part1) {
            String key = card1.expirationDate + "_" + card1.pin;
            List<CreditCard> matchingCards = part2Map.get(key);
            if (matchingCards != null && !matchingCards.isEmpty()) {
                CreditCard card2 = matchingCards.remove(0);
                matches.add(new CreditCard[]{card1, card2});
            }
        }
        
        return matches;
    }
    
    public static List<CreditCard[]> matchLogLinear(List<CreditCard> part1, List<CreditCard> part2) {
        List<CreditCard> sortedPart2 = new ArrayList<>(part2);
        sortedPart2.sort((a, b) -> {
            if (a.expirationDate != b.expirationDate) {
                return Integer.compare(a.expirationDate, b.expirationDate);
            }
            return Integer.compare(a.pin, b.pin);
        });
        
        List<CreditCard[]> matches = new ArrayList<>();
        int j = 0;
        
        for (CreditCard card1 : part1) {
            int left = j;
            int right = sortedPart2.size() - 1;
            int matchIndex = -1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                CreditCard card2 = sortedPart2.get(mid);
                
                if (card2.expirationDate == card1.expirationDate && card2.pin == card1.pin) {
                    matchIndex = mid;
                    right = mid - 1;
                } else if (card2.expirationDate < card1.expirationDate || 
                          (card2.expirationDate == card1.expirationDate && card2.pin < card1.pin)) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            if (matchIndex != -1) {
                matches.add(new CreditCard[]{card1, sortedPart2.remove(matchIndex)});
                j = matchIndex;
            }
        }
        
        return matches;
    }
    
    private static int parseExpirationDate(String expiryDateStr) {
        if (expiryDateStr == null || expiryDateStr.trim().isEmpty()) {
            return 0;
        }
        try {
            String[] parts = expiryDateStr.split("/");
            if (parts.length == 2) {
                int month = Integer.parseInt(parts[0]);
                int year = Integer.parseInt(parts[1]);
                return year * 100 + month;
            }
        } catch (Exception e) {
        }
        return 0;
    }
    
    private static int parsePIN(String pinStr) {
        if (pinStr == null || pinStr.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(pinStr.trim());
        } catch (Exception e) {
            return 0;
        }
    }
    
    private static String extractLast4(String cardNumber) {
        String cleaned = cardNumber.replace("-", "").replace("*", "").replace(" ", "");
        if (cleaned.length() >= 4) {
            return cleaned.substring(cleaned.length() - 4);
        }
        return "";
    }
    
    private static List<CreditCard> loadFromCSV(String filename, boolean hasExpiryAndPIN) {
        List<CreditCard> cards = new ArrayList<>();
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.FileReader(filename));
            
            String line = reader.readLine();
            if (line == null || !line.contains("Credit Card Number")) {
                reader.close();
                return cards;
            }
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(",");
                if (parts.length < 1) continue;
                
                String cardNumber = parts[0].trim();
                
                if (hasExpiryAndPIN && parts.length >= 4) {
                    String expiryDateStr = parts[1].trim();
                    String pinStr = parts[3].trim();
                    
                    int expirationDate = parseExpirationDate(expiryDateStr);
                    int pin = parsePIN(pinStr);
                    
                    if (expirationDate > 0 && pin > 0) {
                        cards.add(new CreditCard(cardNumber, expirationDate, pin));
                    }
                } else if (!hasExpiryAndPIN) {
                    cards.add(new CreditCard(cardNumber, 0, 0));
                }
            }
            
            reader.close();
        } catch (java.io.IOException e) {
            System.err.println("Error reading CSV file " + filename + ": " + e.getMessage());
        }
        return cards;
    }
    
    private static List<CreditCard> loadFromCSV(String filename) {
        return loadFromCSV(filename, true);
    }
    
    private static List<CreditCard> generatePart1(int size) {
        List<CreditCard> cards = new ArrayList<>();
        
        List<CreditCard> part2Cards = loadFromCSV("carddump2.csv", true);
        Map<String, List<CreditCard>> part2Map = new HashMap<>();
        for (CreditCard card : part2Cards) {
            String last4 = extractLast4(card.cardNumber);
            if (!last4.isEmpty()) {
                part2Map.computeIfAbsent(last4, k -> new ArrayList<>()).add(card);
            }
        }
        
        List<CreditCard> part1Raw = loadFromCSV("carddump1.csv", false);
        
        if (!part1Raw.isEmpty() && !part2Map.isEmpty()) {
            for (CreditCard card1 : part1Raw) {
                String last4 = extractLast4(card1.cardNumber);
                List<CreditCard> matched = part2Map.get(last4);
                if (matched != null && !matched.isEmpty()) {
                    CreditCard matchedCard = matched.remove(0);
                    cards.add(new CreditCard(card1.cardNumber, matchedCard.expirationDate, matchedCard.pin));
                }
            }
            
            cards.sort((a, b) -> {
                if (a.expirationDate != b.expirationDate) {
                    return Integer.compare(a.expirationDate, b.expirationDate);
                }
                return Integer.compare(a.pin, b.pin);
            });
        }
        
        if (cards.isEmpty()) {
            int currentExpDate = 202400;
            int currentPin = 1000;
            
            for (int i = 0; i < size; i++) {
                String cardNumber = "4532" + String.format("%012d", i);
                
                if (i > 0 && i % 100 == 0) {
                    currentExpDate++;
                    if (currentExpDate % 100 > 12) {
                        currentExpDate = (currentExpDate / 100 + 1) * 100 + 1;
                    }
                }
                
                if (i > 0 && i % 10 == 0) {
                    currentPin++;
                }
                
                cards.add(new CreditCard(cardNumber, currentExpDate, currentPin));
            }
        }
        
        return cards;
    }
    
    private static List<CreditCard> generatePart2(List<CreditCard> part1) {
        List<CreditCard> part2 = loadFromCSV("carddump2.csv", true);
        
        if (part2.isEmpty()) {
            Random random = ThreadLocalRandom.current();
            
            for (int i = 0; i < part1.size(); i++) {
                CreditCard original = part1.get(i);
                String newCardNumber = "5678" + String.format("%012d", i);
                part2.add(new CreditCard(newCardNumber, original.expirationDate, original.pin));
            }
            
            Collections.shuffle(part2, random);
        } else {
            Collections.shuffle(part2, new Random());
        }
        
        return part2;
    }
    
    private static long measureTime(Runnable function) {
        long startTime = System.nanoTime();
        function.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    
    private static boolean verifyMatches(List<CreditCard[]> matches) {
        for (CreditCard[] match : matches) {
            if (match[0].expirationDate != match[1].expirationDate || 
                match[0].pin != match[1].pin) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("CREDIT CARD MATCHING: LINEAR vs LOG-LINEAR ALGORITHM COMPARISON");
        System.out.println("=".repeat(80));
        System.out.println();
        
        System.out.println("Loading data from CSV files...");
        List<CreditCard> part1 = generatePart1(0);
        List<CreditCard> part2 = generatePart2(part1);
        
        System.out.println("Part 1 loaded: " + part1.size() + " records");
        System.out.println("Part 2 loaded: " + part2.size() + " records");
        System.out.println();
        
        if (part1.isEmpty() || part2.isEmpty()) {
            System.out.println("Warning: CSV files not found or empty. Using generated data.");
            part1 = generatePart1(20000);
            part2 = generatePart2(part1);
        }
        
        int[] sizes = {100, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000};
        
        System.out.printf("%-12s %-20s %-20s %-15s\n", 
            "Size", "Linear Time", "Log-Linear Time", "Winner");
        System.out.println("-".repeat(80));
        
        int crossoverPoint = -1;
        int runs = 5;
        
        for (int size : sizes) {
            if (size > part1.size()) break;
            
            long linearTotal = 0;
            long logLinearTotal = 0;
            
            for (int run = 0; run < runs; run++) {
                List<CreditCard> part1Subset = new ArrayList<>(part1.subList(0, Math.min(size, part1.size())));
                List<CreditCard> part2Subset = new ArrayList<>(part2.subList(0, Math.min(size, part2.size())));
                
                List<CreditCard> part1Copy1 = new ArrayList<>(part1Subset);
                List<CreditCard> part2Copy1 = new ArrayList<>(part2Subset);
                long linearTime = measureTime(() -> {
                    List<CreditCard[]> matches = matchLinear(part1Copy1, part2Copy1);
                    if (!verifyMatches(matches)) {
                        System.err.println("ERROR: Linear algorithm failed verification!");
                    }
                });
                linearTotal += linearTime;
                
                List<CreditCard> part1Copy2 = new ArrayList<>(part1Subset);
                List<CreditCard> part2Copy2 = new ArrayList<>(part2Subset);
                long logLinearTime = measureTime(() -> {
                    List<CreditCard[]> matches = matchLogLinear(part1Copy2, part2Copy2);
                    if (!verifyMatches(matches)) {
                        System.err.println("ERROR: Log-Linear algorithm failed verification!");
                    }
                });
                logLinearTotal += logLinearTime;
            }
            
            long avgLinear = linearTotal / runs;
            long avgLogLinear = logLinearTotal / runs;
            
            String winner;
            if (avgLinear < avgLogLinear) {
                winner = "Linear *";
                if (crossoverPoint == -1) {
                    crossoverPoint = size;
                }
            } else {
                winner = "Log-Linear";
            }
            
            System.out.printf("%-12d %-20s %-20s %-15s\n",
                size,
                formatTime(avgLinear),
                formatTime(avgLogLinear),
                winner);
        }
        
        System.out.println();
        System.out.println("=".repeat(80));
        if (crossoverPoint != -1) {
            System.out.println("Crossover Point: Linear algorithm becomes faster at size " + crossoverPoint);
        } else {
            System.out.println("Note: Linear algorithm was faster even at smallest tested size.");
        }
        System.out.println("=".repeat(80));
        
        System.out.println();
        System.out.println("DETAILED ANALYSIS FOR " + part1.size() + " RECORDS (from CSV):");
        System.out.println("-".repeat(80));
        
        List<CreditCard> part1CopyLinear = new ArrayList<>(part1);
        List<CreditCard> part2CopyLinear = new ArrayList<>(part2);
        long linearTime = measureTime(() -> {
            List<CreditCard[]> matches = matchLinear(part1CopyLinear, part2CopyLinear);
            System.out.println("Linear matches found: " + matches.size());
        });
        
        List<CreditCard> part1CopyLogLinear = new ArrayList<>(part1);
        List<CreditCard> part2CopyLogLinear = new ArrayList<>(part2);
        long logLinearTime = measureTime(() -> {
            List<CreditCard[]> matches = matchLogLinear(part1CopyLogLinear, part2CopyLogLinear);
            System.out.println("Log-Linear matches found: " + matches.size());
        });
        
        System.out.println();
        System.out.println("Linear Algorithm Time: " + formatTime(linearTime));
        System.out.println("Log-Linear Algorithm Time: " + formatTime(logLinearTime));
        System.out.println();
        
        double speedup = (double) logLinearTime / linearTime;
        System.out.printf("Linear algorithm is %.2fx faster for %d records\n", speedup, part1.size());
    }
    
    private static String formatTime(long nanoseconds) {
        if (nanoseconds < 1000) {
            return nanoseconds + " ns";
        } else if (nanoseconds < 1_000_000) {
            return String.format("%.2f μs", nanoseconds / 1000.0);
        } else if (nanoseconds < 1_000_000_000) {
            return String.format("%.2f ms", nanoseconds / 1_000_000.0);
        } else {
            return String.format("%.2f s", nanoseconds / 1_000_000_000.0);
        }
    }
}
