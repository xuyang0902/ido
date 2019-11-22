if redis.call('setnx', KEYS[1], ARGV[1]) == 1 
then 
redis.call('pexpire', KEYS[1], ARGV[2])
return 1
else
return 0
end


