CREATE OR REPLACE FUNCTION public.calculate_distance(
    lat1 double precision,
    lon1 double precision,
    lat2 double precision,
    lon2 double precision)
    RETURNS double precision
    LANGUAGE 'plpgsql'
    COST 100
    IMMUTABLE STRICT PARALLEL UNSAFE
AS $BODY$
BEGIN
RETURN ST_DistanceSphere(ST_MakePoint(lon1, lat1), ST_MakePoint(lon2, lat2));
END;
$BODY$;

ALTER FUNCTION public.calculate_distance(double precision, double precision, double precision, double precision)
    OWNER TO postgres;