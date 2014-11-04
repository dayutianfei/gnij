/**
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ac.iie.s3.lib.rpc;

import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * The interaction required between a NodeInteraction and Client.
 * @since s3-0.0.1
 */
public interface IRPCProxyManager {

  /**
   * Get the dynamic proxy for a node. Methods invoked on this object will be
   * sent via RPC to the server and executed there.
   * 
   * @param node
   *          The node name to look up.
   * @param establishIfNoExists
   * @return a dynamic proxy standing in for the node.
   */
  public VersionedProtocol getProxy(String node, boolean establishIfNoExists);

  /**
   * Notifies the proxy-manager that a a proxy invocation failed.
   * 
   * @param node
   *          Which node had a problem.
   * @param t
   *          The error that occurred (currently unused).
   */
  public void reportNodeCommunicationFailure(String node, Throwable t);

  /**
   * Notifies the proxy-manager that a a proxy invocation succeeded.
   * 
   * @param node
   */
  public void reportNodeCommunicationSuccess(String node);


  public void shutdown();

}
