package com.booking.service.filter;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class FilterService {
//    private final ArenaRepository arenaRepository;
//    private final ModelMapper modelMapper;
//
//    public List<ArenaResultDto> getByFilter(FiltersDto filters) {
//        Set<Arena> arenas;
//        if (filters.getCity() != null) {
//            arenas = arenaRepository.findByCity(filters.getCity());
//        } else {
//            arenas = new HashSet<>(arenaRepository.findAll());
//        }
//
//        if (filters.getFrom() != null && filters.getTo() != null) {
//            arenas = getByReservationByTime(arenas, filters.getFrom(), filters.getTo());
//        }
//
//
//        if (filters.getLongitude() != null && filters.getLatitude() != null) {
//            arenas = getSortedByDistance(arenas, filters.getLongitude(), filters.getLatitude());
//        }
//
//        return arenas.stream()
//                .map(arena -> modelMapper.map(arena, ArenaResultDto.class))
//                .collect(Collectors.toList());
//    }
//
//    private Set<Arena> getByReservationByTime(Set<Arena> arenas, Instant from, Instant to) {
//        Set<Long> arenaIds = arenas.stream().map(Arena::getId).collect(Collectors.toSet());
//        Set<Arena> availableArenas = arenaRepository.findAvailableArenasWithinGivenArenas(new ArrayList<>(arenaIds), from, to);
//        return availableArenas;
//    }
//
//    private Set<Arena> getSortedByDistance(Set<Arena> filtered, Double lng, Double lat) {
//        final Map<Arena, Double> distanceMap = new HashMap<>();
//        int limit = 3;
//        for (Arena arena : filtered) {
//            double arenaLng = arena.getArenaInfo().getAddress().getLongitude();
//            double arenaLat = arena.getArenaInfo().getAddress().getLatitude();
//            double distance = distance(lat, arenaLat, lng, arenaLng, 0, 0);
//            distanceMap.put(arena, distance);
//        }
//        return distanceMap.entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue())
//                .map(Map.Entry::getKey)
//                .limit(limit)
//                .collect(Collectors.toCollection(LinkedHashSet::new));
//    }
//
//    public static double distance(double lat1, double lat2, double lon1,
//                                  double lon2, double el1, double el2) {
//        final int R = 6371;
//        double latDistance = Math.toRadians(lat2 - lat1);
//        double lonDistance = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = R * c * 1000;
//        double height = el1 - el2;
//        distance = Math.pow(distance, 2) + Math.pow(height, 2);
//        return Math.sqrt(distance);
//    }
}
