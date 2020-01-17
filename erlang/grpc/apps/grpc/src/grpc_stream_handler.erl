-module(grpc_stream_handler).

-export([init/3]).
-export([data/4]).
-export([info/3]).
-export([terminate/3]).
-export([early_error/5]).

-record(state, {
        next :: any(),
	bytes_received :: integer()}).

-spec init(cowboy_stream:streamid(), cowboy_req:req(), cowboy:opts())
    -> {cowboy_stream:commands(), #state{}}.
init(StreamID, Req, Opts) ->
    {Commands0, Next} = cowboy_stream:init(StreamID, Req, Opts),
    {Commands0, #state{bytes_received=0, next=Next}}.

-spec data(cowboy_stream:streamid(), cowboy_stream:fin(), cowboy_req:resp_body(), State)
    -> {cowboy_stream:commands(), State} when State::#state{}.
data(StreamID, IsFin, Data, State0=#state{bytes_received = Received,
                                          next=Next0}) ->
    {Commands0, Next} = cowboy_stream:data(StreamID, IsFin, Data, Next0),
    Size = size(Data),
    TotalReceived = Size + Received,
    %% io:format("received ~p bytes, total now: ~p~n", [Size, TotalReceived]),
    case TotalReceived > 32767 of
        false ->
            {Commands0, State0#state{next=Next, 
                                     bytes_received = TotalReceived}};
        true ->
            Increment = TotalReceived, %% top up
            %% io:format("sending WINDOW_UPDATEs (~p bytes)~n", [Increment]),
            {[{flow, Increment} | Commands0],
             State0#state{next=Next, bytes_received = 0}}
    end.

-spec info(cowboy_stream:streamid(), any(), State)
    -> {cowboy_stream:commands(), State} when State::#state{}.
info(StreamID, Info, State0=#state{next=Next0}) ->
    {Commands0, Next} = cowboy_stream:info(StreamID, Info, Next0),
    Commands = remove_date_and_server(Commands0),
    {Commands, State0#state{next=Next}}.

-spec terminate(cowboy_stream:streamid(), cowboy_stream:reason(), #state{}) -> any().
terminate(StreamID, Reason, #state{next=Next}) ->
    cowboy_stream:terminate(StreamID, Reason, Next).

-spec early_error(cowboy_stream:streamid(), cowboy_stream:reason(),
    cowboy_stream:partial_req(), Resp, cowboy:opts()) -> Resp
    when Resp::cowboy_stream:resp_command().
early_error(StreamID, Reason, PartialReq, Resp, Opts) ->
    cowboy_stream:early_error(StreamID, Reason, PartialReq, Resp, Opts).


%%-----------------------------------------------------------------------------
%% Internal functions
%%-----------------------------------------------------------------------------

%% cowboy adds headers for "date" and "server", these must be removed.
remove_date_and_server(Commands) ->
    F = fun({headers, Status, Headers}) ->
                {headers, Status, maps:without([<<"date">>, <<"server">>], Headers)};
           (Other) ->
                Other
        end,
    [F(C) || C <- Commands].
