/*
 * Copyright 2015 Bridje Framework.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bridje.ioc.thls;

/**
 * This service allows you to execute an action with a group of data objects
 * that will be available in the thread.
 */
public interface ThlsService
{
    /**
     * This method puts all the data objects on the internal thread local
     * storage an executes the {@link ThlsAction}.
     *
     * @param <T>    The type of the resulting object for the action.
     * @param <D>    The type of the data to be put in the thread.
     * @param action The action to be executed.
     * @param cls    The class of the data to be put in the thread.
     * @param data   The data that must be available for the action.
     *
     * @return The object returned by the {@link ThlsAction#execute()} method.
     */
    public <T, D> T doAs(ThlsAction<T> action, Class<D> cls, D data);

    /**
     * This method puts all the data objects on the internal thread local
     * storage an executes the {@link ThlsAction}.
     *
     * @param <T>    The type of the resulting object for the action.
     * @param <D>    The type of the data to be put in the thread.
     * @param <E>    The type of the first exception throw by the execution of
     *               this action.
     * @param action The action to be executed.
     * @param cls    The class of the data to be put in the thread.
     * @param data   The data that must be available for the action.
     *
     * @return The object returned by the {@link ThlsActionException#execute()}
     *         method.
     *
     * @throws E Exception throw by the action.
     */
    public <T, D, E extends Throwable> T doAsEx(ThlsActionException<T, E> action, Class<D> cls, D data) throws E;
    
    /**
     * This method puts all the data objects on the internal thread local
     * storage an executes the {@link ThlsAction}.
     *
     * @param <T>    The type of the resulting object for the action.
     * @param <D>    The type of the data to be put in the thread.
     * @param <E>    The type of the first exception throw by the execution of
     *               this action.
     * @param <E2>   The type of the second exception throw by the execution of
     *               this action.
     * @param action The action to be executed.
     * @param cls    The class of the data to be put in the thread.
     * @param data   The data that must be available for the action.
     *
     * @return The object returned by the {@link ThlsActionException2#execute()}
     *         method.
     *
     * @throws E  Exception throw by the action.
     * @throws E2 The second exception throw by the action.
     */
    public <T, D, E extends Throwable, E2 extends Throwable> T doAsEx2(ThlsActionException2<T, E, E2> action, Class<D> cls, D data) throws E, E2;

    /**
     * Get the last object of the specified class that was put in the thread
     * local storage.
     *
     * @param <T> The type of the object to look for.
     * @param cls The class of the object to look for.
     *
     * @return The last object of the specified class that was put in the thread
     *         local storage, or null if none can be found.
     */
    <T> T get(Class<T> cls);

}
