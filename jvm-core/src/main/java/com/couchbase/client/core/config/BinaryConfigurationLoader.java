/**
 * Copyright (C) 2014 Couchbase, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */

package com.couchbase.client.core.config;

import com.couchbase.client.core.cluster.Cluster;
import com.couchbase.client.core.environment.Environment;
import com.couchbase.client.core.message.binary.GetBucketConfigRequest;
import com.couchbase.client.core.message.binary.GetBucketConfigResponse;
import com.couchbase.client.core.service.ServiceType;
import reactor.core.composable.Promise;
import reactor.function.Function;

import java.net.InetSocketAddress;

public class BinaryConfigurationLoader extends AbstractConfigurationLoader {

	public BinaryConfigurationLoader(Environment env, InetSocketAddress node, String bucket, String password,
		Cluster cluster) {
		super(env, ServiceType.BINARY, node, bucket, password, cluster);
	}

	@Override
	Promise<String> loadRawConfig() {
		GetBucketConfigRequest req = new GetBucketConfigRequest(node(), bucket(), password());
		return cluster().<GetBucketConfigResponse>send(req).map(new Function<GetBucketConfigResponse, String>() {
			@Override
			public String apply(GetBucketConfigResponse response) {
				return response.content();
			}
		});
	}

}