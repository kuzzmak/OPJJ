connect 'jdbc:derby://localhost:1527/votingDB;user=sa;password=sapwd22;create=true';
CALL SYSCS_UTIL.SYSCS_CREATE_USER('ivica', 'ivo');
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers', 'sa,ivica');
disconnect;
