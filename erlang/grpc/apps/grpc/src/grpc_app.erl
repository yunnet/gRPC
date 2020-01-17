%%%-------------------------------------------------------------------
%% @doc grpc public API
%% @end
%%%-------------------------------------------------------------------

-module(grpc_app).

-behaviour(application).

-export([start/2, stop/1]).

start(_StartType, _StartArgs) ->
    grpc_sup:start_link().

stop(_State) ->
    ok.

%% internal functions
