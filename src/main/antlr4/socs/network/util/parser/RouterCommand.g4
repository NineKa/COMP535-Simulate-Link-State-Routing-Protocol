grammar RouterCommand;

command :   cmdDetect
        |   cmdAttach
        |   cmdAttachFile
        |   cmdStart
        |   cmdNeighbors
        |   cmdQuit
        |   cmdDebug
        ;

cmdDetect : CMD_DETECT simulateIP=IP_ADDRESS;
cmdAttach : CMD_ATTACH processIP=IP_ADDRESS processPort=NUMBER simulateIP=IP_ADDRESS weight=NUMBER;
cmdAttachFile : CMD_ATTACH path=QUOTED_STRING weight=NUMBER;
cmdStart : CMD_START;
cmdNeighbors : CMD_NEIGHBORS;
cmdQuit : CMD_QUIT;
cmdDebug : CMD_DEBUG select=CMD_DEBUG_SELECT;

IP_ADDRESS : [0-9][0-9]?[0-9]? '.' [0-9][0-9]?[0-9]? '.' [0-9][0-9]?[0-9]? '.' [0-9][0-9]?[0-9]?;
NUMBER : '0' | ( [0-9][0-9]* );

fragment ESCAPED_QUOTE : '\\"';
QUOTED_STRING :   '"' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '"';

CMD_DETECT : 'detect';
CMD_DISCONNECT : 'disconnect';
CMD_QUIT : 'quit';
CMD_ATTACH : 'attach';
CMD_START : 'start';
CMD_CONNECT : 'connect';
CMD_NEIGHBORS : 'neighbors';

CMD_DEBUG : 'debug';
CMD_DEBUG_SELECT : 'info' | 'lsd';

NEWLINE : [\r\n] -> skip;
WHITESPACE : [ \t]+ -> skip;
